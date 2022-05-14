package com.kingdee.ofd;

import com.alibaba.fastjson.JSONObject;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Jdom2Test {
    public static void main(String[] args) throws IOException, JDOMException {

        String ss = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><condition><queryPK>com.kingdee.eas.base.ssc.app.JobQuery</queryPK><columnModel>isExpired,completeTme,orgID.name,orgID.id,rownumber,subject,jobState,infoMsg,receiveTime,joblevel.name,remainderTime,createTime,expireState,jobFlagType,id,billID,bussAttrID.id,shareCenterID,bussAttrID.name,certificationStatus,personName,adminOrg,autoApprove</columnModel><includePager>true</includePager><sidx></sidx><sord>asc</sord><filterItems>bussAttrID.id = 'nullData' AND jobPoolingType IN ('2') and jobState IN ('0', '2', '3', '4', '5', '6', '10') </filterItems><userId>ZKFR9jBBReSXJeWHHrO2vxO33n8=</userId><isDemandQuery>false</isDemandQuery><sorterItems>remainderTime asc,createTime desc</sorterItems><rows>20</rows><page>1</page></condition>" ;
        Map<String, Object> stringObjectMap = parseXML(ss);
        Object o = JSONObject.toJSON(stringObjectMap);
        System.out.println(o);
        System.out.println("ok");


    }

    public static Map<String, Object> parseXML(String xml)
            throws JDOMException, IOException {
        /** *用于存放节点的信息** */
        Map<String, Object> map = new HashMap<String, Object>();
        /** *创建一个新的字符串*** */
        StringReader xmlReader = new StringReader(xml);
        /** **创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入 */
        InputSource xmlSource = new InputSource(xmlReader);
        /** *创建一个SAXBuilder* */
        SAXBuilder builder = new SAXBuilder();
        /** *通过输入源SAX构造一个Document** */
        Document doc = builder.build(xmlSource);
        /** *获得根节点** */
        Element elt = doc.getRootElement();
        /** *获得根节点下面的所有子节点*** */
        List<Element> child = elt.getChildren();
        //支持的格式  eg:  <?xml version='1.0' encoding='UTF-8'?><operation><action></action><params><jobId></jobId></params></operation>
        /** *遍历出body节点下面所有的子节点，节点名称和内容用put到map* */
        for (Element childEle : child) {
            if(childEle.getChildren()!=null && childEle.getChildren()!=null && childEle.getChildren().size()>0){
                Map<String, Object> tempMap = new HashMap<String, Object>();
                List<Element> tempChild = childEle.getChildren();
                for (Element tempChildEle : tempChild) {
                    tempMap.put(tempChildEle.getName(), tempChildEle.getText());
                }
                map.put(childEle.getName(), tempMap);
            }else{
                map.put(childEle.getName(), childEle.getText());
            }
        }
        return map;
    }

}
