//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.10.04 at 06:45:21 PM MSK 
//


package ru.geekbrains.backend.buisness.soap.categories;

import ru.geekbrains.backend.buisness.soap.products.Product;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for category complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="category"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="alias" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="parentCategoryAlias" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="subCategories" type="{http://gbshop.ru/soap/ws/products}product" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "category", propOrder = {
        "id",
        "title",
        "alias",
        "parentCategoryAlias",
        "subCategories"
})
public class Category {

    protected long id;
    @XmlElement(required = true)
    protected String title;
    @XmlElement(required = true)
    protected String alias;
    @XmlElement(required = true)
    protected String parentCategoryAlias;
    @XmlElement(required = true)
    protected List<Product> subCategories;

    /**
     * Gets the value of the id property.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * Gets the value of the title property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the alias property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Sets the value of the alias property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setAlias(String value) {
        this.alias = value;
    }

    /**
     * Gets the value of the parentCategoryAlias property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getParentCategoryAlias() {
        return parentCategoryAlias;
    }

    /**
     * Sets the value of the parentCategoryAlias property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setParentCategoryAlias(String value) {
        this.parentCategoryAlias = value;
    }

    /**
     * Gets the value of the subCategories property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subCategories property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubCategories().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Product }
     */
    public List<Product> getSubCategories() {
        if (subCategories == null) {
            subCategories = new ArrayList<Product>();
        }
        return this.subCategories;
    }

}
