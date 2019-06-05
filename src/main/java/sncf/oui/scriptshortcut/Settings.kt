package sncf.oui.scriptshortcut

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

class Settings(private val project: Project) : Configurable {

    private var pathLabel: JLabel? = null
    private var argumentLabel: JLabel? = null
    private var shortcutLabel: JLabel? = null

    private var pathField: JTextField? = null
    private var argumentField: JTextField? = null
    private var shortcutField: JLabel? = null

    private var mainPanel: JPanel? = null

    private var modified = false

    override fun isModified(): Boolean {
        return modified
    }

    override fun getDisplayName(): String {
        return "Script Shortcut"
    }

    override fun apply() {
        saveFields()
    }

    private fun saveFields() {
        val config = UserConfiguration.getInstance(project)
        config.scriptPath = pathField?.text ?: ""
        config.arguments = argumentField?.text ?: ""

        modified = false
    }

    override fun createComponent(): JComponent? {
        initListener()

        loadConfig()

        return mainPanel
    }

    private fun initListener() {

        val docListener = object : DocumentListener {
            override fun changedUpdate(e: DocumentEvent?) {
                modified = true
            }

            override fun insertUpdate(e: DocumentEvent?) {
                modified = true
            }

            override fun removeUpdate(e: DocumentEvent?) {
                modified = true
            }
        }

        pathField?.document?.addDocumentListener(docListener)
        argumentField?.document?.addDocumentListener(docListener)
    }

    private fun loadConfig() {
        val config = UserConfiguration.getInstance(project)
        pathField?.text = config.scriptPath
        argumentField?.text = config.arguments
    }

}
