/*
Copyright (c) 2007-2009, Yusuke Yamamoto
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of the Yusuke Yamamoto nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY Yusuke Yamamoto ``AS IS'' AND ANY
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL Yusuke Yamamoto BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package twitter4j.http;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import twitter4j.TwitterException;
import twitter4j.org.json.JSONObject;
import twitter4j.org.json.JSONException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * A data class representing HTTP Response
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public class Response implements java.io.Serializable {
    static DocumentBuilder builder = null;
    static {

        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    private int statusCode;
    private Document response = null;
    private String responseString = null;
    private InputStream is;
    private static final long serialVersionUID = 6190279542077827227L;
    private SAXException saxe = null;

    public Response(int statusCode, InputStream is) throws IOException {
        this.statusCode = statusCode;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuffer buf = new StringBuffer();
            String line;
            while (null != (line = br.readLine())) {
                buf.append(line).append("\n");
            }
            this.responseString = buf.toString();
            this.is = new ByteArrayInputStream(responseString.getBytes("UTF-8"));
        } catch (NullPointerException ignore) {
            throw new IOException(ignore.getMessage());
        }
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String asString() {
        return responseString;
    }

    public InputStream asStream() {
        return is;
    }

    public Document asDocument() throws TwitterException{
        if(null == saxe && null == response){
            try {
                this.response = builder.parse(new ByteArrayInputStream(
                        responseString.getBytes("UTF-8")));
            } catch (SAXException saxe) {
                this.saxe = saxe;
            } catch (IOException ioe) {
                //should never reach here
                throw new TwitterException("Twitter returned a non-XML response", ioe);
            }
        }
        if(null != saxe){
            throw new TwitterException("Twitter returned a non-XML response", saxe);
        }
        return response;
    }

    public JSONObject asJSONObject() throws TwitterException {
        try {
            return new JSONObject(this.responseString);
        } catch (JSONException jsone) {
            throw new TwitterException(jsone.getMessage() + ":" + this.responseString);
        }
    }

    public InputStreamReader asReader() {
        try {
            return new InputStreamReader(is, "UTF-8");
        } catch (java.io.UnsupportedEncodingException uee) {
            return new InputStreamReader(is);
        }
    }

    @Override
    public String toString(){
        return responseString;
    }
}
