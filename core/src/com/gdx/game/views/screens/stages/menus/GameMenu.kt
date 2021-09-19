package com.gdx.game.views.screens.stages.menus

import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.gdx.game.Main
import com.gdx.game.system.defaults.FileExtensions
import com.gdx.game.system.defaults.Paths
import com.gdx.game.system.defaults.messages.errors.Errors
import com.gdx.game.system.defaults.views.screens.widgets.Dimensions
import com.gdx.game.system.defaults.views.screens.widgets.Fonts
import com.gdx.game.system.defaults.views.screens.widgets.Names
import com.gdx.game.system.defaults.views.world.MapArchiveDefaults
import com.gdx.game.system.settings.config.Settings
import com.gdx.game.system.storage.DirectoryScanner
import com.gdx.game.system.storage.mapping.MapZipArchiveLoader
import com.gdx.game.system.storage.mapping.archives.ThumbnailExtractionPath
import com.gdx.game.system.storage.mapping.archives.ZipExtractor
import com.gdx.game.views.screens.GameScreen
import com.gdx.game.views.screens.MenuScreen
import com.gdx.game.views.screens.compositing.Adjuster
import com.gdx.game.views.screens.compositing.Creator
import com.gdx.game.views.screens.compositing.Proportioner
import com.gdx.game.views.screens.compositing.adder.StageAdder
import com.gdx.game.views.screens.compositing.adder.TableAdder
import com.gdx.game.views.screens.stages.MenuFloating
import com.gdx.game.views.screens.widgets.Paddings
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.util.logging.Level
import java.util.logging.Logger

internal class GameMenu(skin: Skin) : HorizontalMenu(skin) {
    companion object {
        private val LOGGER = Logger.getLogger(EditMenu::class.qualifiedName)
    }

    private var numberOfFileButtons = 0
    private var numberOfFileButtonRows = 1

    private val scanner: DirectoryScanner
    private val grpFileButtons: ButtonGroup<ImageTextButton>
    private val lblNoFiles: Label

    init {
        // create
        val btnSingle = TextButton("Single", skin, Names.Button.BUTTON)
        val btnSplitscreen = TextButton("Splitscreen", skin, Names.Button.BUTTON)
        val btnLan = TextButton("LAN", skin, Names.Button.BUTTON)
        val btnOnline = TextButton("Online", skin, Names.Button.BUTTON)
//        val tabDriftCity = TextButton("Drift City", skin, Names.Button.TAB)
//        val tabSnowyMountains = TextButton("Snowy Mountains", skin, Names.Button.TAB)
//        val tabTropyIsland = TextButton("Tropy Island", skin, Names.Button.TAB)
        val filterFontStyle = Creator.createFilterFontStyle(skin)
        val btnFilterOfficial = TextButton("Official", filterFontStyle)
        val btnFilterYours = TextButton("Yours", filterFontStyle)
        val btnFilterDownloaded = TextButton("Downloaded", filterFontStyle)
        val btnFilterAll = TextButton("All", filterFontStyle)

        grpFileButtons = ButtonGroup()
        lblNoFiles = Label("There are no maps...", skin)

        // adjust
        Proportioner.setFontSize(skin, Fonts.LABEL, 0.000521f)
        btnSplitscreen.isDisabled = true
        btnLan.isDisabled = true
        btnOnline.isDisabled = true
//        tabDriftCity.isDisabled = true
//        tabSnowyMountains.isDisabled = true
//        tabTropyIsland.isChecked = true
        btnFilterYours.isDisabled = true
        btnFilterDownloaded.isDisabled = true
        btnFilterAll.isDisabled = true
        lblNoFiles.isVisible = false

        // link
        link(btnSingle, btnSplitscreen, btnLan, btnOnline)

        // add
        addButton(btnSingle)
        addButton(btnSplitscreen)
        addButton(btnLan)
        addButton(btnOnline)
//        addTab(tabDriftCity)
//        addTab(tabSnowyMountains)
//        addTab(tabTropyIsland)
        addFilter(btnFilterOfficial)
        addFilter(btnFilterYours)
        addFilter(btnFilterDownloaded)
        addFilterAll(btnFilterAll)
        placeFilter(Vector2(1.105f, 0.052f), MenuFloating.CENTER)
        StageAdder.addTo(lblNoFiles, this, Vector2(0.491f, 0.448f), Align.center, MenuFloating.CENTER)

        // scan and add
        scanner = DirectoryScanner()
        findAndShowFiles(skin)
    }

    private fun findAndShowFiles(skin: Skin) {
        val files = scanner.getFiles(Paths.User.Folders.MAPS, FileExtensions.MAP_ARCHIVE)
        if (files.isEmpty()) {
            lblNoFiles.isVisible = true
        }

        val totalNumberOfFileButtonRows = Math.ceil(files.size / 5.0f.toDouble()).toInt()
        activateScrollingIfNecessary(totalNumberOfFileButtonRows)

        for (file in files) {
            addMenuFileButton(file, skin, totalNumberOfFileButtonRows)
        }

        loadFileButtonImages(files)
        cleanUpTemporaryThumbnails()
    }

    private fun activateScrollingIfNecessary(totalNumberOfFileButtonRows: Int) {
        if (totalNumberOfFileButtonRows <= 2) {
            scrollPane.setScrollingDisabled(false, true)
            scrollUpButton.isDisabled = true
            scrollDownButton.isDisabled = true
        }
    }

    private fun addMenuFileButton(file: Path, skin: Skin, totalNumberOfFileButtonRows: Int) {
        val fileName = FileExtensions.stripExtension(file.fileName.toString())
        val imageTextButton = Creator.createMenuFileButton(fileName, skin)
        val scrollPane = scrollPane
        val table = scrollPane.actor as Table

        // adjust
        Adjuster.adjustMenuFileButtons(arrayOf(imageTextButton), -0.13f)
        Proportioner.setScrollBarSizes(scrollPane, Dimensions.SCROLLBAR_KNOB)

        // add
        if (numberOfFileButtons > 0 && numberOfFileButtons % 5 == 0) {
            table.add().row()
            numberOfFileButtonRows++
        }

        val menuFileButtonDim = Dimensions.Button.MENU_FILE;

        when (numberOfFileButtonRows) {
            1 -> TableAdder.addTo(table, imageTextButton, menuFileButtonDim, Paddings(0f, 0f, 0.014f, 0.042f))  // first row
            totalNumberOfFileButtonRows -> TableAdder.addTo(table, imageTextButton, menuFileButtonDim, Paddings(0.014f, 0f, 0f, 0.042f))  // last row
            else -> TableAdder.addTo(table, imageTextButton, menuFileButtonDim, Paddings(0.014f, 0f, 0.014f, 0.042f))
        }

        if (totalNumberOfFileButtonRows > 2) {
            scrollPane.setScrollbarsVisible(true)
        } else {
            scrollPane.setScrollingDisabled(true, true)
        }

        grpFileButtons.add(imageTextButton)
        numberOfFileButtons++
    }

    private fun link(btnSingle: Button, btnSplitscreen: Button, btnLan: Button, btnOnline: Button) {
        btnSingle.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                val checkedIndex = grpFileButtons.checkedIndex
                val mapArchives = scanner.getFiles(Paths.User.Folders.MAPS, FileExtensions.MAP_ARCHIVE)
                val path = mapArchives[checkedIndex]

                Main.instance.screen = GameScreen(
                        Settings(Paths.User.Folders.Files.SYSTEM, Paths.User.Folders.Files.CONTROLS),
                        MapZipArchiveLoader.load(path.toAbsolutePath().toString()).map, false)
            }
        })

        btnSplitscreen.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {}
        })

        btnLan.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {}
        })

        btnOnline.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {}
        })
    }

    private fun loadFileButtonImages(files: List<Path>) {
        val zipExtractor = ZipExtractor()

        for (i in files.indices) {
            loadFileButtonImage(files, i, zipExtractor)
        }
    }

    private fun loadFileButtonImage(files: List<Path>, index: Int, zipExtractor: ZipExtractor) {
        try {
            val archivePath = files[index].toAbsolutePath().toString()
            val destinationPath = Paths.Temp.Maps.THUMBNAILS
            val archiveName = FileExtensions.stripExtension(files[index].fileName.toString())

            zipExtractor.extractIfNecessary(archivePath, MapArchiveDefaults.THUMBNAIL_NAME, destinationPath, archiveName)
            loadImageFromTempFolder(index, destinationPath, archiveName)
        } catch (e: IOException) {
            LOGGER.log(Level.SEVERE, Errors.Map.COULD_NOT_EXTRACT_THUMBNAILS, e)
        }
    }

    private fun loadImageFromTempFolder(index: Int, destinationPath: String, archiveName: String) {
        val imageTextButton = grpFileButtons.buttons[index]
        val style = ImageTextButton.ImageTextButtonStyle(imageTextButton.style)

        style.imageUp = TextureRegionDrawable(Texture(FileHandle(ThumbnailExtractionPath.getFileDestinationPath(destinationPath, MapArchiveDefaults.THUMBNAIL_NAME, archiveName))))

        imageTextButton.style = style
        Adjuster.adjustMenuFileButtonImage(imageTextButton, 0.217f, 0.217f, -0.021f)
    }

    private fun cleanUpTemporaryThumbnails() {
        val thumbnailsPath = Paths.Temp.Maps.THUMBNAILS
        val mapsPath = Paths.User.Folders.MAPS
        val thumbnailFilePaths = scanner.getFiles(thumbnailsPath, FileExtensions.THUMBNAIL)

        val mapFileNames = scanner.getFiles(mapsPath, FileExtensions.MAP_ARCHIVE).map { path: Path -> FileExtensions.stripExtension(path.fileName.toString()) }

        if (thumbnailFilePaths.size == mapFileNames.size) { // for each thumbnail one map
            return
        } // else thumbnailFilePaths.size() > mapFileNames.size()

        deleteThumbnailsWithoutAMap(thumbnailFilePaths, mapFileNames)
    }

    private fun deleteThumbnailsWithoutAMap(thumbnailFilePaths: List<Path>, mapFileNames: List<String?>) {
        for (thumbnailFilePath in thumbnailFilePaths) {
            val thumbnailMapName = ThumbnailExtractionPath.getMapName(thumbnailFilePath)

            if (thumbnailMapName in mapFileNames) {
                return
            }

            try {
                Files.delete(thumbnailFilePath)
            } catch (e: IOException) {
                LOGGER.log(Level.SEVERE, Errors.Map.COULD_NOT_DELETE_THUMBNAIL, e)
            }
        }
    }
}