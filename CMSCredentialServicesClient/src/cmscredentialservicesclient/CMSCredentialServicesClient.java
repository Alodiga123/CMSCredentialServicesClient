/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmscredentialservicesclient;

import com.alodiga.cms.json.card.AssignPhysicalCardResponse;
import com.alodiga.cms.json.card.AssignVirtualCardResponse;
import com.alodiga.cms.credential.service.utils.EncriptedRsa;
import static com.alodiga.cms.credential.service.utils.EncriptedRsa.encrypt;
import com.alodiga.cms.json.card.Constants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Moisés Graterol
 */
public class CMSCredentialServicesClient {

   public static void main(String[] args) throws Exception {
        CMSCredentialServicesClient cardCredential = new CMSCredentialServicesClient();
        //cardCredential.assignVirtualCard("484", "CC", "0205C000000003", "20201005", "19680204", "SMITH+%20944", "H", "Benito%20Juarez", "03230", "525585258744", "NANCY.RAMELLA@CREDENCIAL.COM.MX", "F", "RAMELLA", "NANCY", "VACS691110UY9", "0002", "N", "95000,00", "Cerrito%20740", "H", "Benito%20Juárez", "03230", "10", "IVJVG1GEJeY%2B8sthmXXWo50vwEC%2B6MsK/plOBEtjZoRXLhvg8atTGL6PdlSnS59PEiWmb2/MGIqgdetPUzydTjErk25adtoTnRGLajn2iYjuc9rPVrsFXJNAxcQEF9BUv4YrD4FY%2BHdUlBjWorDDMuH5Jtsoazg0kVLZ6BaawXA=");
        cardCredential.assignVirtualCard("484", "CC", "0205C000000003", "20201005", "19680204", "SMITH+%20944", "H", "Benito%20Juarez", "03230", "525585258744", "NANCY.RAMELLA@CREDENCIAL.COM.MX", "F", "RAMELLA", "NANCY", "VACS691110UY9", "0002", "N", "95000,00", "Cerrito%20740", "H", "Benito%20Juárez", "03230", "10");
        //cardCredential.assignPhysicalCard("484", "5255852587440055", "525812584475", "Cerrito%20740", "H", "Benito%20Ju%C3%A1rez", "03230", "10", "S", "NANCY.RAMELLA@CREDENCIAL.COM.MX", "eYIryr64lLE%2BClVzaF3SfwpZRT5CYtPKylFHrMZASQg/EyTlFf0JUapkb0T7kBB7sqxiVwmY%2BlCeErg9vo7JAsGnzFPPbf6KKpzLb5uLZgjBuCUkAc6N8BNn/wXFa4g5KVX/KI90ghuk0RErJw/K23tNh2KSE0svUOdEm0elYCM=");
    }
  
   public AssignVirtualCardResponse assignVirtualCard(String Ptr, String Tdc, String Ndc, String Fal, 
            String Fnc, String Dct, String Dbt, String Dlt, String Cpn, String Ntc, String Mce, 
            String Cst, String Aps, String Nbs, String Cuii, String Afff, String Mgt, String Icel, 
            String Dcte, String Dpte, String Dlte, String Cpne, String Cple) throws MalformedURLException, IOException, Exception {
       
        HttpURLConnection connection = null;
        InputStream is = null;
        String responseString = "";
        String outputString = "";
        String Penc = "";
        
        try {
            //Se establece la conexión con la URL de Credencial Argentina
            java.net.URL url = new java.net.URL("https://master.credencial.com.ar/cgi-bin/nwwcgitst/PRUWEB/CWWS005B");
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setConnectTimeout(Constants.CONNECTION_TIMEOUT);
            
            //Encriptar la clave para conectarse al WebService de Credencial
            String encryptedPassword = Base64.encodeBase64String(encrypt(Constants.PASSWORD_CREDENCIAL_WEB_SERVICES_TEST,Constants.PUBLIC_KEY_TEST));
            encryptedPassword = encryptedPassword.replace("+", "%2B");
            Penc = encryptedPassword;
            
            //Se estructura la cadena con la información de los parámetros
            StringBuilder postParam = new StringBuilder();
            postParam.append("OPS"); //Es el tipo de operación. Para este servicio corresponde "RegAlw" (Longitud máxima = 6)
            postParam.append("=");
            postParam.append(Constants.OPS);
            postParam.append("&");

            postParam.append("COD"); //Es el valor de la identidad Empresarial. En este caso corresponde "758" (Longitud máxima = 3)
            postParam.append("=");
            postParam.append(Constants.COD);
            postParam.append("&");

            postParam.append("PTR"); //Es donde se debe informar el código de pais ISO 3166-1 numérico de tres posiciones (Longitud máxima = 3)
            postParam.append("=");
            postParam.append(Ptr);
            postParam.append("&");

            postParam.append("TDC"); //Tipo de documento de identidad (Verificar el documento guia con las tablas SPC) ejp CC = "CEDULA DE CIUDADANIA" (Longitud máxima = 3)
            postParam.append("=");
            postParam.append(Tdc);
            postParam.append("&");

            postParam.append("NDC"); //Es el valor del numero de documento de identidad (Longitud máxima = 30)
            postParam.append("=");
            postParam.append(Ndc);
            postParam.append("&");

            postParam.append("FAL"); //Es donde se debe informar la Fecha de Alta (Longitud máxima = 8)
            postParam.append("=");
            postParam.append(Fal);
            postParam.append("&");

            postParam.append("FNC");// Aqui es la fecha de nacimiento del solicitante (Longitud máxima = 8)
            postParam.append("=");
            postParam.append(Fnc);
            postParam.append("&");

            postParam.append("DCT");//Dirección de calle y numero de calle (Juntos) (Longitud máxima = 30)
            postParam.append("=");
            postParam.append(Dct);
            postParam.append("&");

            postParam.append("DBT");//Es el valor de código de provincia(Los codigos de referencia se debe verificar en el docmento de guia) (Longitud máxima = 1)
            postParam.append("=");
            postParam.append(Dbt);
            postParam.append("&");

            postParam.append("DLT");//Es el valor de la descripción de la localidad, por ejemplo para provincia H (Ciudad de Mexico) puede ser 'Azcapotzalco', 'Benito Juárez', etc. (Longitud máxima = 25)
            postParam.append("=");
            postParam.append(Dlt);
            postParam.append("&");

            postParam.append("CPN");//Es el valor del código postal de la ciudad (Longitud máxima = 8)
            postParam.append("=");
            postParam.append(Cpn);
            postParam.append("&");

            postParam.append("NTC");//Es el valor del número del teléfono móvil (Longitud máxima = 20)
            postParam.append("=");
            postParam.append(Ntc);
            postParam.append("&");

            postParam.append("MCE");//Es el valor del correo electrónico (Longitud máxima = 60)
            postParam.append("=");
            postParam.append(Mce);
            postParam.append("&");

            postParam.append("CST");//Es el valor del sexo de la persona (Longitud máxima = 1)
            postParam.append("=");
            postParam.append(Cst);
            postParam.append("&");

            postParam.append("APS");//Es el valor del apellido de la persona (Longitud máxima = 20)
            postParam.append("=");
            postParam.append(Aps);
            postParam.append("&");

            postParam.append("NBS");//Es el valor del nombre de la persona (Longitud máxima = 20)
            postParam.append("=");
            postParam.append(Nbs);
            postParam.append("&");

            postParam.append("CUI");//Es el valor que corresponde al RIF en el caso de Venezuela y al RFC en caso de Mexico (Longitud máxima = 15)
            postParam.append("=");
            postParam.append(Cuii);
            postParam.append("&");
 
            postParam.append("PRO");//Código de Producto. Información proporcionada por Credencial (Longitud máxima = 4)
            postParam.append("=");
            postParam.append(Constants.PRO);
            postParam.append("&");

            postParam.append("AFF");//Codigo de Afinación. Información proporcionada por Credencial (Longitud máxima = 4)
            postParam.append("=");
            postParam.append(Afff);
            postParam.append("&");

            postParam.append("MGT");//Es el valor de la grabación de tarjeta. Los posibles valores son 'S'(Se da de alta una tarjeta fisica) y 'N'(Se da de alta una tarjeta virtual)
            postParam.append("=");
            postParam.append(Mgt);
            postParam.append("&");

            postParam.append("ICEL");//Es el valor para el importe de carga anual y/o límite
            postParam.append("=");
            postParam.append(Icel);
            postParam.append("&");

            postParam.append("DCTE");//Es el valor de la dirección (calle y nro) de entrega de la tarjeta (Longitud máxima = 30)
            postParam.append("=");
            postParam.append(Dcte);
            postParam.append("&");

            postParam.append("DPTE");//Codigo de Provincia(Verificar el documento guia con los códigos de provincias) Ejemplo: P = MORELOS (Longitud máxima = 1)
            postParam.append("=");
            postParam.append(Dpte);
            postParam.append("&");

            postParam.append("DLTE");//Es el valor de la localidad de entrega (Longitud máxima = 25)
            postParam.append("=");
            postParam.append(Dlte);
            postParam.append("&");

            postParam.append("CPNE");//Es el valor del código postal de la dirección de entrega (Longitud máxima = 8)
            postParam.append("=");
            postParam.append(Cpne);
            postParam.append("&");

            postParam.append("CPLE");//Es valor del piso/local/oficina/departamento de entrega (Longitud máxima = 10)
            postParam.append("=");
            postParam.append(Cple);
            postParam.append("&");

            postParam.append("PENC");//Password encriptado otorgado al cliente por Credencial Processing (Longitud máxima = 200)
            postParam.append("=");
            postParam.append(Penc);
            
            //Se envían los parámetros a la solicitud HTTP
            String paramPost = postParam.toString();
            System.out.println("paramPost " + paramPost);
            connection.setFixedLengthStreamingMode(paramPost.getBytes().length);
            PrintWriter out = new PrintWriter(connection.getOutputStream());
            out.print(paramPost);
            out.close();
            
            //Obtener la respuesta del Servicio 
            try {
                is = connection.getInputStream();
            } catch (IOException ioe) {
                if (connection instanceof HttpURLConnection) {
                    HttpURLConnection httpConn = (HttpURLConnection) connection;
                    int statusCode = httpConn.getResponseCode();
                    System.out.println(httpConn.getResponseCode());
                    System.out.println(statusCode);
                    if (statusCode != 200) {
                        is = httpConn.getErrorStream();
                    }
                }
            }
            
            //Se guarda la respuesta del servicio en outputString
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            while ((responseString = rd.readLine()) != null) {
                outputString = outputString + responseString;
            }
            
            System.out.println("out: " + outputString);
            //Convierte la respuesta outputString en un Document (XML)
            Document doc = parseXmlFile(outputString);
            //Se obtiene la información del XML (Respuesta del Servicio)
            NodeList nodesResponse = doc.getElementsByTagName("Response");
            Element elementResponse = (Element) nodesResponse.item(0);
            NodeList codigo = elementResponse.getElementsByTagName("Codigo");
            Element Codigo = (Element) codigo.item(0);
            NodeList timeStamp = elementResponse.getElementsByTagName("TimeStamp");
            Element TimeStamp = (Element) timeStamp.item(0);
            NodeList codigoRespuesta = elementResponse.getElementsByTagName("CodigoRespuesta");
            Element CodigoRespuesta = (Element) codigoRespuesta.item(0);
            NodeList errorCodigo = elementResponse.getElementsByTagName("ErrorCodigo");
            Element ErrorCodigo = (Element) errorCodigo.item(0);
            NodeList errorDescripcion = elementResponse.getElementsByTagName("ErrorDescripcion");
            Element ErrorDescripcion = (Element) errorDescripcion.item(0);
            NodeList nodesRegAlta = doc.getElementsByTagName("RegAlta");
            Element elementRegAlta = (Element) nodesRegAlta.item(0);
            NodeList pais = elementRegAlta.getElementsByTagName("Pais");
            Element Pais = (Element) pais.item(0);
            NodeList tDoc = elementRegAlta.getElementsByTagName("TDoc");
            Element TDoc = (Element) tDoc.item(0);
            NodeList nDoc = elementRegAlta.getElementsByTagName("NDoc");
            Element NDoc = (Element) nDoc.item(0);
            NodeList fAlta = elementRegAlta.getElementsByTagName("FAlta");
            Element FAlta = (Element) fAlta.item(0);
            NodeList fNac = elementRegAlta.getElementsByTagName("FNac");
            Element FNac = (Element) fNac.item(0);
            NodeList denC = elementRegAlta.getElementsByTagName("DenC");
            Element DenC = (Element) denC.item(0);
            NodeList denB = elementRegAlta.getElementsByTagName("DenB");
            Element DenB = (Element) denB.item(0);
            NodeList denL = elementRegAlta.getElementsByTagName("DenL");
            Element DenL = (Element) denL.item(0);
            NodeList codP = elementRegAlta.getElementsByTagName("CodP");
            Element CodP = (Element) codP.item(0);
            NodeList nTel = elementRegAlta.getElementsByTagName("NTel");
            Element NTel = (Element) nTel.item(0);
            NodeList mail = elementRegAlta.getElementsByTagName("Mail");
            Element Mail = (Element) mail.item(0);
            NodeList sexo = elementRegAlta.getElementsByTagName("Sexo");
            Element Sexo = (Element) sexo.item(0);
            NodeList apN = elementRegAlta.getElementsByTagName("ApN");
            Element ApN = (Element) apN.item(0);
            NodeList nmB = elementRegAlta.getElementsByTagName("NmB");
            Element NmB = (Element) nmB.item(0);
            NodeList cui = elementRegAlta.getElementsByTagName("Cui");
            Element Cui = (Element) cui.item(0);
            NodeList pro = elementRegAlta.getElementsByTagName("Pro");
            Element Pro = (Element) pro.item(0);
            NodeList aff = elementRegAlta.getElementsByTagName("Aff");
            Element Aff = (Element) aff.item(0);
            NodeList graTar = elementRegAlta.getElementsByTagName("GraTar");
            Element GraTar = (Element) graTar.item(0);
            NodeList vnd = elementRegAlta.getElementsByTagName("Vnd");
            Element Vnd = (Element) vnd.item(0);
            NodeList imp = elementRegAlta.getElementsByTagName("Imp");
            Element Imp = (Element) imp.item(0);
            NodeList alias = elementRegAlta.getElementsByTagName("Alias");
            Element Alias = (Element) alias.item(0);
            NodeList ctasig = elementRegAlta.getElementsByTagName("Ctasig");
            Element Ctasig = (Element) ctasig.item(0);
            NodeList clabe = elementRegAlta.getElementsByTagName("Clabe");
            Element Clabe = (Element) clabe.item(0);
            NodeList callent = elementRegAlta.getElementsByTagName("Callent");
            Element Callent = (Element) callent.item(0);
            NodeList localent = elementRegAlta.getElementsByTagName("Localent");
            Element Localent = (Element) localent.item(0);
            NodeList cPosent = elementRegAlta.getElementsByTagName("CPosent");
            Element CPosent = (Element) cPosent.item(0);
            NodeList plodent = elementRegAlta.getElementsByTagName("Plodent");
            Element Plodent = (Element) plodent.item(0);
            NodeList provent = elementRegAlta.getElementsByTagName("Provent");
            Element Provent = (Element) provent.item(0);

            //Se guarda en AssignVirtualCardResponse la respuesta del servicio
            AssignVirtualCardResponse assignVirtualCardResponse = new AssignVirtualCardResponse();
            try {
                assignVirtualCardResponse.setCodigo(getCharacterDataFromElement(Codigo));
                assignVirtualCardResponse.setTimeStamp(getCharacterDataFromElement(TimeStamp));
                assignVirtualCardResponse.setCodigoRespuesta(getCharacterDataFromElement(CodigoRespuesta));
                assignVirtualCardResponse.setErrorCodigo(getCharacterDataFromElement(ErrorCodigo));
                assignVirtualCardResponse.setErrorDescripcion(getCharacterDataFromElement(ErrorDescripcion));
                assignVirtualCardResponse.setPais(getCharacterDataFromElement(Pais));
                assignVirtualCardResponse.setTDoc(getCharacterDataFromElement(TDoc));
                assignVirtualCardResponse.setNDoc(getCharacterDataFromElement(NDoc));
                assignVirtualCardResponse.setFAlta(getCharacterDataFromElement(FAlta));
                assignVirtualCardResponse.setFNac(getCharacterDataFromElement(FNac));
                assignVirtualCardResponse.setDenC(getCharacterDataFromElement(DenC));
                assignVirtualCardResponse.setDenB(getCharacterDataFromElement(DenB));
                assignVirtualCardResponse.setDenL(getCharacterDataFromElement(DenL));
                assignVirtualCardResponse.setCodP(getCharacterDataFromElement(CodP));
                assignVirtualCardResponse.setNTel(getCharacterDataFromElement(NTel));
                assignVirtualCardResponse.setMail(getCharacterDataFromElement(Mail));
                assignVirtualCardResponse.setSexo(getCharacterDataFromElement(Sexo));
                assignVirtualCardResponse.setApN(getCharacterDataFromElement(ApN));
                assignVirtualCardResponse.setNmB(getCharacterDataFromElement(NmB));
                assignVirtualCardResponse.setCui(getCharacterDataFromElement(Cui));
                assignVirtualCardResponse.setPro(getCharacterDataFromElement(Pro));
                assignVirtualCardResponse.setAff(getCharacterDataFromElement(Aff));
                assignVirtualCardResponse.setGraTar(getCharacterDataFromElement(GraTar));
                assignVirtualCardResponse.setVnd(getCharacterDataFromElement(Vnd));
                assignVirtualCardResponse.setImp(getCharacterDataFromElement(Imp));
                assignVirtualCardResponse.setAlias(getCharacterDataFromElement(Alias));
                assignVirtualCardResponse.setCtasig(getCharacterDataFromElement(Ctasig));
                assignVirtualCardResponse.setClabe(getCharacterDataFromElement(Clabe));
                assignVirtualCardResponse.setCallent(getCharacterDataFromElement(Callent));
                assignVirtualCardResponse.setLocalent(getCharacterDataFromElement(Localent));
                assignVirtualCardResponse.setCPosent(getCharacterDataFromElement(CPosent));
                assignVirtualCardResponse.setPlodent(getCharacterDataFromElement(Plodent));
                assignVirtualCardResponse.setProvent(getCharacterDataFromElement(Provent));

            } catch (Exception e) {
                e.printStackTrace();
            }
            rd.close();
            System.out.println("assignVirtualCardResponse.getCodigo() " + assignVirtualCardResponse.getCodigoRespuesta());
            System.out.println(assignVirtualCardResponse.getErrorCodigo());
            System.out.println(assignVirtualCardResponse.getTimeStamp());
            return new AssignVirtualCardResponse(assignVirtualCardResponse.getCodigo(), assignVirtualCardResponse.getTimeStamp(), assignVirtualCardResponse.getCodigoRespuesta(), assignVirtualCardResponse.getErrorCodigo(), assignVirtualCardResponse.getErrorDescripcion(), assignVirtualCardResponse.getPais(), assignVirtualCardResponse.getTDoc(), assignVirtualCardResponse.getNDoc(), assignVirtualCardResponse.getFAlta(), assignVirtualCardResponse.getFNac(), assignVirtualCardResponse.getDenC(), assignVirtualCardResponse.getDenB(), assignVirtualCardResponse.getDenL(), assignVirtualCardResponse.getCodP(), assignVirtualCardResponse.getNTel(), assignVirtualCardResponse.getMail(), assignVirtualCardResponse.getSexo(), assignVirtualCardResponse.getApN(), assignVirtualCardResponse.getNmB(), assignVirtualCardResponse.getCui(), assignVirtualCardResponse.getPro(), assignVirtualCardResponse.getAff(), assignVirtualCardResponse.getGraTar(), assignVirtualCardResponse.getVnd(), assignVirtualCardResponse.getImp(), assignVirtualCardResponse.getAlias(), assignVirtualCardResponse.getCtasig(), assignVirtualCardResponse.getClabe(), assignVirtualCardResponse.getCallent(), assignVirtualCardResponse.getLocalent(), assignVirtualCardResponse.getCPosent(), assignVirtualCardResponse.getPlodent(), assignVirtualCardResponse.getProvent());
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            throw new MalformedURLException();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new IOException();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public AssignPhysicalCardResponse assignPhysicalCard(String Ptr, String Alantt, String Alactt, 
            String Dcte, String Dpte, String Dlte, String Cpne, String Cple, String Mgt, 
            String Mce) throws MalformedURLException, IOException, Exception {

        HttpURLConnection connection = null;
        InputStream is = null;
        String responseString = "";
        String outputString = "";
        String Penc = "";
        
        try {
            //Se establece la conexión con la URL
            java.net.URL url = new java.net.URL("https://master.credencial.com.ar/cgi-bin/nwwcgitst/PRUWEB/CWWS005B");
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            
            //Encriptar la clave para conectarse al WebService de Credencial
            String encryptedPassword = Base64.encodeBase64String(encrypt(Constants.PASSWORD_CREDENCIAL_WEB_SERVICES_TEST,Constants.PUBLIC_KEY_TEST));
            encryptedPassword = encryptedPassword.replace("+", "%2B");
            Penc = encryptedPassword;
            
            //Se estructura la cadena con la información de los parámetros
            StringBuilder sb = new StringBuilder();
            sb.append("OPS");//Es el tipo de operación. Para este servicio corresponde "RegMod" (Longitud máxima = 6)
            sb.append("=");
            sb.append(Constants.OPS_ASSIGNPHYSICALCARD);
            sb.append("&");

            sb.append("COD");//Es el valor de la identidad Empresarial. En este caso corresponde "758" (Longitud máxima = 3)
            sb.append("=");
            sb.append(Constants.COD);
            sb.append("&");

            sb.append("PTR");//Es donde se debe informar el código de pais ISO 3166-1 numérico de tres posiciones (Longitud máxima = 3)
            sb.append("=");
            sb.append(Ptr);
            sb.append("&");

            sb.append("ALANT");//Es este campo se envía el alias devuelto en el servicio anterior el cual representa el sinónimo del nro. de tarjeta.
            sb.append("=");
            sb.append(Alantt);
            sb.append("&");

            sb.append("ALACT");//En el caso de querer modificar el nro.de celular informado, se deberá informarlo en este campo, en la respuesta del servicio, en este campo se retornará el nuevo alias.
            sb.append("=");
            sb.append(Alactt);
            sb.append("&");
                               //Proporcionar solo si MGT = S
            sb.append("DCTE");//Es el valor de la dirección (calle y nro) de entrega de la tarjeta (Longitud máxima = 30)
            sb.append("=");
            sb.append(Dcte);
            sb.append("&");

            sb.append("DPTE");//Codigo de Provincia(Verificar el documento guia con los códigos de provincias) Ejemplo: P = MORELOS (Longitud máxima = 1)
            sb.append("=");
            sb.append(Dpte);
            sb.append("&");
                               //Proporcionar solo si MGT = S
            sb.append("DLTE");//Es el valor de la localidad de entrega (Longitud máxima = 25) 
            sb.append("=");
            sb.append(Dlte);
            sb.append("&");
                               //Proporcionar solo si MGT = S
            sb.append("CPNE");//Es el valor del código postal de la dirección de entrega (Longitud máxima = 8)
            sb.append("=");
            sb.append(Cpne);
            sb.append("&");
                               //Proporcionar solo si MGT = S
            sb.append("CPLE");//En este campo se informa el PLOD de la dirección de entrega de la tarjeta física, el PLOD es piso/local/oficina/departamento, ejemplo: 'PISO 8 DTO A' (Longitud máxima = 10)
            sb.append("=");
            sb.append(Cple);
            sb.append("&");

            sb.append("MGT");//Es el valor de la grabación de tarjeta. Los posibles valores son 'S'(Se da de alta una tarjeta fisica) y 'N'(Se da de alta una tarjeta virtual) (Longitud máxima = 1)
            sb.append("=");
            sb.append(Mgt);
            sb.append("&");

            sb.append("MCE");//Es el valor del correo electrónico (Longitud máxima = 60)
            sb.append("=");
            sb.append(Mce);
            sb.append("&");

            sb.append("PENC");//Es el valor del password encriptado, el password sin encriptar se proporcionara por credencial (Longitud máxima = 200)
            sb.append("=");
            sb.append(Penc);

            //Se envían los parámetros a la solicitud HTTP
            String post = sb.toString(); 
            connection.setFixedLengthStreamingMode(post.getBytes().length);
            PrintWriter out = new PrintWriter(connection.getOutputStream());
            out.print(post);
            out.close();
            
            //Get Response  
            try {
                is = connection.getInputStream();
            } catch (IOException ioe) {
                if (connection instanceof HttpURLConnection) {
                    HttpURLConnection httpConn = (HttpURLConnection) connection;
                    int statusCode = httpConn.getResponseCode();
                    System.out.println(httpConn.getResponseCode());
                    System.out.println(statusCode);
                    if (statusCode != 200) {
                        is = httpConn.getErrorStream();
                    }
                }
            }
            
            //Comentario
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            while ((responseString = rd.readLine()) != null) {
                outputString = outputString + responseString;
            }
            
            //System.out.println("out: " + outputString);
            Document doc = parseXmlFile(outputString);
            NodeList nodesResponse = doc.getElementsByTagName("Response");
            Element elementResponse = (Element) nodesResponse.item(0);
            NodeList codigo = elementResponse.getElementsByTagName("Codigo");
            Element Codigo = (Element) codigo.item(0);
            NodeList timeStamp = elementResponse.getElementsByTagName("TimeStamp");
            Element TimeStamp = (Element) timeStamp.item(0);
            NodeList codigoRespuesta = elementResponse.getElementsByTagName("CodigoRespuesta");
            Element CodigoRespuesta = (Element) codigoRespuesta.item(0);
            NodeList errorCodigo = elementResponse.getElementsByTagName("ErrorCodigo");
            Element ErrorCodigo = (Element) errorCodigo.item(0);
            NodeList errorDescripcion = elementResponse.getElementsByTagName("ErrorDescripcion");
            Element ErrorDescripcion = (Element) errorDescripcion.item(0);
            NodeList nodesRegAlta = doc.getElementsByTagName("RegMod");
            Element elementRegAlta = (Element) nodesRegAlta.item(0);
            NodeList pais = elementRegAlta.getElementsByTagName("Pais");
            Element Pais = (Element) pais.item(0);
            NodeList ruc = elementRegAlta.getElementsByTagName("Ruc");
            Element Ruc = (Element) ruc.item(0);
            NodeList alant = elementRegAlta.getElementsByTagName("Alant");
            Element Alant = (Element) alant.item(0);
            NodeList alact = elementRegAlta.getElementsByTagName("Alact");
            Element Alact = (Element) alact.item(0);
            NodeList callent = elementRegAlta.getElementsByTagName("Callent");
            Element Callent = (Element) callent.item(0);
            NodeList localent = elementRegAlta.getElementsByTagName("Localent");
            Element Localent = (Element) localent.item(0);
            NodeList cPosent = elementRegAlta.getElementsByTagName("CPosent");
            Element CPosent = (Element) cPosent.item(0);
            NodeList plodent = elementRegAlta.getElementsByTagName("Plodent");
            Element Plodent = (Element) plodent.item(0);
            NodeList provent = elementRegAlta.getElementsByTagName("Provent");
            Element Provent = (Element) provent.item(0);
            NodeList graTar = elementRegAlta.getElementsByTagName("GraTar");
            Element GraTar = (Element) graTar.item(0);
            NodeList mail = elementRegAlta.getElementsByTagName("Mail");
            Element Mail = (Element) mail.item(0);
            NodeList panc = elementRegAlta.getElementsByTagName("Panc");
            Element Panc = (Element) panc.item(0);

            AssignPhysicalCardResponse assignPhysicalCardResponse = new AssignPhysicalCardResponse();
            try {
                assignPhysicalCardResponse.setCodigo(getCharacterDataFromElement(Codigo));
                assignPhysicalCardResponse.setTimeStamp(getCharacterDataFromElement(TimeStamp));
                assignPhysicalCardResponse.setCodigoRespuesta(getCharacterDataFromElement(CodigoRespuesta));
                assignPhysicalCardResponse.setErrorCodigo(getCharacterDataFromElement(ErrorCodigo));
                assignPhysicalCardResponse.setErrorDescripcion(getCharacterDataFromElement(ErrorDescripcion));
                assignPhysicalCardResponse.setPais(getCharacterDataFromElement(Pais));
                assignPhysicalCardResponse.setRuc(getCharacterDataFromElement(Ruc));
                assignPhysicalCardResponse.setAlant(getCharacterDataFromElement(Alant));
                assignPhysicalCardResponse.setAlact(getCharacterDataFromElement(Alact));
                assignPhysicalCardResponse.setCallent(getCharacterDataFromElement(Callent));
                assignPhysicalCardResponse.setLocalent(getCharacterDataFromElement(Localent));
                assignPhysicalCardResponse.setCPosent(getCharacterDataFromElement(CPosent));
                assignPhysicalCardResponse.setPlodent(getCharacterDataFromElement(Plodent));
                assignPhysicalCardResponse.setProvent(getCharacterDataFromElement(Provent));
                assignPhysicalCardResponse.setGraTar(getCharacterDataFromElement(GraTar));
                assignPhysicalCardResponse.setMail(getCharacterDataFromElement(Mail));
                assignPhysicalCardResponse.setPanc(getCharacterDataFromElement(Panc));
            } catch (Exception e) {
                e.printStackTrace();
            }
            rd.close();
            System.out.println("timestamp: " + assignPhysicalCardResponse.getTimeStamp());
            System.out.println("codigo: " + assignPhysicalCardResponse.getCodigo());
            System.out.println("codigoRespuesta: " + assignPhysicalCardResponse.getCodigoRespuesta());
            return new AssignPhysicalCardResponse(assignPhysicalCardResponse.getCodigo(), assignPhysicalCardResponse.getTimeStamp(), assignPhysicalCardResponse.getCodigoRespuesta(), assignPhysicalCardResponse.getErrorCodigo(), assignPhysicalCardResponse.getErrorDescripcion(), assignPhysicalCardResponse.getPais(), assignPhysicalCardResponse.getRuc(), assignPhysicalCardResponse.getAlant(), assignPhysicalCardResponse.getAlact(), assignPhysicalCardResponse.getCallent(), assignPhysicalCardResponse.getLocalent(), assignPhysicalCardResponse.getCPosent(), assignPhysicalCardResponse.getPlodent(), assignPhysicalCardResponse.getProvent(), assignPhysicalCardResponse.getGraTar(), assignPhysicalCardResponse.getMail(), assignPhysicalCardResponse.getPanc());

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            throw new MalformedURLException();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new IOException();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    // parse XML
    private static Document parseXmlFile(String in) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return null;
    }
    
}
