
package org.xzc.services2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>sayHiToUser complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="sayHiToUser">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="user2" type="{http://service.xzc.org/}user" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sayHiToUser", propOrder = {
    "user2"
})
public class SayHiToUser {

    protected User user2;

    /**
     * 获取user2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link User }
     *     
     */
    public User getUser2() {
        return user2;
    }

    /**
     * 设置user2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link User }
     *     
     */
    public void setUser2(User value) {
        this.user2 = value;
    }

}
