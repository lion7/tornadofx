package tornadofx.tests

import javafx.beans.property.SimpleDoubleProperty
import javafx.collections.FXCollections
import javafx.scene.control.Label
import javafx.stage.Stage
import org.junit.Assert
import org.junit.Test
import org.testfx.api.FxToolkit
import tornadofx.*
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class BindingTests {
    val primaryStage: Stage = FxToolkit.registerPrimaryStage()

    @Test
    fun testBindingFormat() {
        val property = SimpleDoubleProperty(Math.PI)

        val label = Label()
        label.bind(property, format = DecimalFormat("#0.0", DecimalFormatSymbols.getInstance(Locale.ENGLISH)))

        Assert.assertEquals("3.1", label.text)
    }

    @Test
    fun observableListBinding() {
        val elements = FXCollections.observableArrayList("Hello", "World")
        val binding = tornadofx.stringBinding(elements, elements) { this.joinToString(" ")}
        val uielement = Label().apply { bind(binding) }
        Assert.assertEquals("Hello World", uielement.text)
        elements.setAll("Hello", "Changed")
        Assert.assertEquals("Hello Changed", uielement.text)
    }

    @Test
    fun nestedBinding() {
        val father = Person("Mr Father", 50)
        val stepFather = Person("Mr Step Father", 40)
        val child = Person("Firstborn Child", 18)
        child.parent = father

        val fatherName = child.parentProperty().select(Person::nameProperty)
        Assert.assertEquals("Mr Father", fatherName.value)
        fatherName.value = "Mister Father"
        Assert.assertEquals("Mister Father", father.name)
        child.parent = stepFather
        Assert.assertEquals("Mr Step Father", fatherName.value)
    }
}