import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.CDATASection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileReader;
import java.util.*;


public class CreateXMLFileJava {

    public static final String IBMQ = "/home/jibendu.paul/SCRIPT_GEN/GENERATED_XML/IBMQ_SCRIPT.xml";
    public static final String RABBITMQ = "/home/jibendu.paul/SCRIPT_GEN/GENERATED_XML/RABBITMQ_SCRIPT.xml";
    public static void main(String argv[]) throws Exception{


        Scanner in = new Scanner(System.in);
        FileReader reader = new FileReader("data");
        Properties p = new Properties();
        p.load(reader);
        String s1 = p.getProperty("TenantId");
        String s2 =p.getProperty("Id");
        String s3 = p.getProperty("NttyStsCd");
        String s4 = p.getProperty("FctvFr");
        String s5 = p.getProperty("TrnsprtId");
        String s6 = p.getProperty("TrnsprtTp");
        String s7 = p.getProperty("Scp");
        String s8 = p.getProperty("Ctgy");
        String header = p.getProperty("headerName");
        String ack1 = p.getProperty("cknowledgementModeName");
        ack1 = ack1.toUpperCase();
        int ack = 0;
        if(ack1.equals("AUTO"))
            ack = 1;
        else if (ack1.equals("DUBS"))
            ack = 2;
        else if(ack1.equals("CLIENT"))
            ack = 3;
        String exchange = p.getProperty("exchangeType");
        String topo = p.getProperty("topologyRecoveryEnabled");
        String auto = p.getProperty("automaticRecoveryEnabled");

        //-------------------------IBMQ------------------------------------

            //System.out.println("--------IBM DETAILS------------");
            String cfg_ib = "<from uri=jms:\"";
            String temp = "";
            if(s5.equals("NONBATCHTRANSPORT"))
            {

                cfg_ib = cfg_ib + "nonbatchin?";
                cfg_ib =  cfg_ib + "concurrentConsumers={{NONBATCHIN_CCOUNT}}&amp;acknowledgementModeName=";
            }
            else
            {
                cfg_ib = cfg_ib + s5.toLowerCase()+ "?";
                cfg_ib = cfg_ib + "concurrentConsumers=" + "{{" + s5.toLowerCase() + "_CCOUNT}}&amp;acknowledgementModeName=";
            }

            if(ack == 1)
                cfg_ib = cfg_ib + "AUTO_ACKNOWLEDGE\"/>";
            else if(ack == 2)
                cfg_ib = cfg_ib + "DUPS_OK_ACKNOWLEDGE\"/>";
            else if(ack == 3)
                cfg_ib = cfg_ib + "CLIENT_ACKNOWLEDGE\"/>";

            temp = temp + "<setHeader headerName=";
            temp = temp + "\"" +header +"\"" +  ">";
            cfg_ib = cfg_ib + temp;
            cfg_ib = cfg_ib + "<constant>" + s5 +"</constant></setHeader><to uri=\"volante:CallInstructionReceiver2\" /";
            String s9 = cfg_ib;

            //----------------------RABBITMQ-----------------------------------

            String s10 = "";
            //System.out.println("-----RABBITMQ DETAILS--------");
            String cfg = "<from uri=\"amqprabbit:";
            if(s5.equals("NONBATCHTRANSPORT"))
                cfg = cfg + "nonbatchin?exchangeType=";
            else
                cfg = cfg + s5.toLowerCase() +"?exchangeType=";
            cfg = cfg + exchange.toLowerCase() +"&amp;autoAck=false&amp;autoDelete=false&amp;prefetchCount={{PREFETCH_COUNT}}&amp;prefetchEnabled=true&amp;prefetchGlobal=false&amp;";
            topo = topo.toUpperCase();
            auto = auto.toUpperCase();
            if(topo.equals("ENABLE"))
                cfg = cfg + "topologyRecoveryEnabled={{ENABLE_RABBIT_TOPOLOGY_RECOVERY}}&amp;";
            else if (topo.equals("DISABLE"))
                cfg = cfg + "topologyRecoveryEnabled={{DISABLE_RABBIT_TOPOLOGY_RECOVERY}}&amp;";
            if(auto.equals("ENABLE"))
                cfg = cfg + "automaticRecoveryEnabled={{ENABLE_RABBIT_AUTO_RECOVERY}}&amp;";
            else if(auto.equals("DISABLE"))
                cfg = cfg + "automaticRecoveryEnabled={{DISABLE_RABBIT_AUTO_RECOVERY}}&amp;";

        if(s5.equals("NONBATCHTRANSPORT"))
        {
            cfg =  cfg + "concurrentConsumers={{NONBATCHIN_CCOUNT}}&amp;";
            cfg = cfg +  "threadPoolSize={{NONBATCHIN_CCOUNT}}&amp;";
            cfg = cfg + "routingKey=nonbatchin&amp;queue=nonbatchin\" />";
        }
        else
        {
            cfg = cfg + "concurrentConsumers={{" + s5.toLowerCase() +"_CCOUNT}}&amp;";
            cfg = cfg + "threadPoolSize={{" + s5.toLowerCase() + "_CCOUNT}}&amp;";
            cfg = cfg + "routingKey=" + s5.toLowerCase() +"&amp;queue=" + s5.toLowerCase()+ "\"/>";
        }

        String temp_ra = "";
        temp_ra = temp_ra + "<setHeader headerName=";
        temp_ra = temp_ra + "\"" +header +"\"" +  ">";
        cfg = cfg + temp;
        cfg= cfg + "<constant>" + s5 +"</constant></setHeader><to uri=\"volante:CallInstructionReceiver2\" />";

        s10 = cfg;

            try {
                /*----------------------IBMQ--------------------------------------------
                * ----------------------IBMQ--------------------------------------------*/


                DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
                Document document = documentBuilder.newDocument();


                Element root = document.createElement("TransportContextList");
                document.appendChild(root);
                Element context = document.createElement("TransportContext");
                root.appendChild(context);

                Element tenant = document.createElement("TenantId");
                tenant.appendChild(document.createTextNode(s1));
                context.appendChild(tenant);

                Element ID = document.createElement("Id");
                ID.appendChild(document.createTextNode(s2));
                context.appendChild(ID);

                Element nttystatus = document.createElement("NttyStsCd");
                nttystatus.appendChild(document.createTextNode(s3));
                context.appendChild(nttystatus);

                Element fctv = document.createElement("FctvFr");
                fctv.appendChild(document.createTextNode(s4));
                context.appendChild(fctv);

                Element transportID = document.createElement("TrnsprtId");
                transportID.appendChild(document.createTextNode(s5));
                context.appendChild(transportID);

                Element transportTP = document.createElement("TrnsprtTp");
                transportTP.appendChild(document.createTextNode(s6));
                context.appendChild(transportTP);

                Element scp = document.createElement("Scp");
                scp.appendChild(document.createTextNode(s7));
                context.appendChild(scp);

                Element catrgory = document.createElement("Ctgy");
                catrgory.appendChild(document.createTextNode(s8));
                context.appendChild(catrgory);

                Element transportConfig = document.createElement("TrnsprtCfgtn");
                CDATASection cdataSection = document.createCDATASection(s9);
                transportConfig.appendChild(cdataSection);
                context.appendChild(transportConfig);

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource domSource = new DOMSource(document);
                StreamResult streamResult = new StreamResult(new File(IBMQ));
                transformer.transform(domSource, streamResult);



                /*--------------------------------------------RABBITMQ------------------------------------------------
                ----------------------------------------------RABBITMQ------------------------------------------------*/


                DocumentBuilderFactory documentFactory_ra = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder_ra = documentFactory_ra.newDocumentBuilder();
                Document document_ra = documentBuilder_ra.newDocument();

                Element root_ra = document_ra.createElement("TransportContextList");
                document_ra.appendChild(root_ra);

                Element context_ra = document_ra.createElement("TransportContext");
                root_ra.appendChild(context_ra);


                Element tenant_ra = document_ra.createElement("TenantId");
                tenant_ra.appendChild(document_ra.createTextNode(s1));
                context_ra.appendChild(tenant_ra);

                Element ID_ra = document_ra.createElement("Id");
                ID_ra.appendChild(document_ra.createTextNode(s2));
                context_ra.appendChild(ID_ra);

                Element nttystatus_ra = document_ra.createElement("NttyStsCd");
                nttystatus_ra.appendChild(document_ra.createTextNode(s3));
                context_ra.appendChild(nttystatus_ra);

                Element fctv_ra = document_ra.createElement("FctvFr");
                fctv_ra.appendChild(document_ra.createTextNode(s4));
                context_ra.appendChild(fctv_ra);

                Element transportID_ra = document_ra.createElement("TrnsprtId");
                transportID_ra.appendChild(document_ra.createTextNode(s5));
                context_ra.appendChild(transportID_ra);

                Element transportTP_ra = document_ra.createElement("TrnsprtTp");
                transportTP_ra.appendChild(document_ra.createTextNode(s6));
                context_ra.appendChild(transportTP_ra);

                Element scp_ra = document_ra.createElement("Scp");
                scp_ra.appendChild(document_ra.createTextNode(s7));
                context_ra.appendChild(scp_ra);

                Element catrgory_ra = document_ra.createElement("Ctgy");
                catrgory_ra.appendChild(document_ra.createTextNode(s8));
                context_ra.appendChild(catrgory_ra);

                Element transportConfig_ra = document_ra.createElement("TrnsprtCfgtn");
                context_ra.appendChild(transportConfig_ra);

                CDATASection cdataSection_ra = document_ra.createCDATASection(s10);
                transportConfig_ra.appendChild(cdataSection_ra);
                context_ra.appendChild(transportConfig_ra);

                TransformerFactory transformerFactory_ra = TransformerFactory.newInstance();
                Transformer transformer_ra = transformerFactory_ra.newTransformer();
                DOMSource domSource_ra = new DOMSource(document_ra);
                StreamResult streamResult_ra = new StreamResult(new File(RABBITMQ));
                transformer_ra.transform(domSource_ra, streamResult_ra);


                    System.out.println("Done creating   XML FileS");


            } catch (ParserConfigurationException pce) {
                pce.printStackTrace();
            } catch (TransformerException tfe) {
                tfe.printStackTrace();
            }
        }
    }
