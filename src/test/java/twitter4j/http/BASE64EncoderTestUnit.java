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

import junit.framework.TestCase;
/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public class BASE64EncoderTestUnit extends TestCase {
    public BASE64EncoderTestUnit(String name) {
        super(name);
    }


    public void testEncode() {
        BASE64Encoder encoder = new BASE64Encoder();
        assertEquals("QUJDREVGRw==", encoder.encode("ABCDEFG".getBytes()));
        assertEquals("VGhlIHF1aWNrIGJyb3duIGZveCBqdW1wZWQgb3ZlciB0aGUgbGF6eSBkb2cu", encoder.encode("The quick brown fox jumped over the lazy dog.".getBytes()));
        assertEquals("bGVhc3VyZS4=", encoder.encode("leasure.".getBytes()));
        assertEquals("ZWFzdXJlLg==", encoder.encode("easure.".getBytes()));
        assertEquals("YXN1cmUu", encoder.encode("asure.".getBytes()));
        assertEquals("c3VyZS4=", encoder.encode("sure.".getBytes()));
//        assertEquals("", encoder.encode("".getBytes()));
//        assertEquals("", encoder.encode("".getBytes()));
//        assertEquals("", encoder.encode("".getBytes()));
//        assertEquals("", encoder.encode("".getBytes()));
    }
}
