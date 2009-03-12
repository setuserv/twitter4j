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
package twitter4j;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A data class representing one single status of a user.
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public class Status extends TwitterResponse implements java.io.Serializable {
    private Date createdAt;
    private long id;
    private String text;
    private String source;
    private boolean isTruncated;
    private long inReplyToStatusId;
    private int inReplyToUserId;
    private boolean isFavorited;
    private static final long serialVersionUID = 1608000492860584608L;

    /*package*/Status(Element elem, Twitter twitter) throws TwitterException {
        super();
        ensureRootNodeNameIs("status", elem);
        user = new User((Element) elem.getElementsByTagName("user").item(0),
                twitter);
        id = getChildLong("id", elem);
        text = getChildText("text", elem);
        source = getChildText("source", elem);
        createdAt = getChildDate("created_at", elem);
        isTruncated = getChildBoolean("truncated", elem);
        inReplyToStatusId = getChildInt("in_reply_to_status_id", elem);
        inReplyToUserId = getChildInt("in_reply_to_user_id", elem);
        isFavorited = getChildBoolean("favorited", elem);
    }

    /**
     * Return the created_at
     *
     * @return created_at
     * @since Twitter4J 1.1.0
     */

    public Date getCreatedAt() {
        return this.createdAt;
    }

    /**
     * Returns the id of the status
     *
     * @return the id
     */
    public long getId() {
        return this.id;
    }

    /**
     * Returns the text of the status
     *
     * @return the text
     */
    public String getText() {
        return this.text;
    }

    /**
     * Returns the source
     *
     * @return the source
     * @since Twitter4J 1.0.4
     */
    public String getSource() {
        return this.source;
    }


    /**
     * Test if the status is truncated
     *
     * @return true if truncated
     * @since Twitter4J 1.0.4
     */
    public boolean isTruncated() {
        return isTruncated;
    }

    /**
     * Returns the in_reply_tostatus_id
     *
     * @return the in_reply_tostatus_id
     * @since Twitter4J 1.0.4
     */
    public long getInReplyToStatusId() {
        return inReplyToStatusId;
    }

    /**
     * Returns the in_reply_user_id
     *
     * @return the in_reply_tostatus_id
     * @since Twitter4J 1.0.4
     */
    public int getInReplyToUserId() {
        return inReplyToUserId;
    }

    /**
     * Test if the status is favorited
     *
     * @return true if favorited
     * @since Twitter4J 1.0.4
     */
    public boolean isFavorited() {
        return isFavorited;
    }


    private User user = null;

    /**
     * Return the user
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /*package*/
    static List<Status> constructStatuses(Document doc,
                                          Twitter twitter) throws TwitterException {
        if (isRootNodeNilClasses(doc)) {
            return new ArrayList<Status>(0);
        } else {
            try {
                ensureRootNodeNameIs("statuses", doc);
                NodeList list = doc.getDocumentElement().getElementsByTagName(
                        "status");
                int size = list.getLength();
                List<Status> statuses = new ArrayList<Status>(size);
                for (int i = 0; i < size; i++) {
                    Element status = (Element) list.item(i);
                    statuses.add(new Status(status, twitter));
                }
                return statuses;
            } catch (TwitterException te) {
                ensureRootNodeNameIs("nil-classes", doc);
                return new ArrayList<Status>(0);
            }
        }
    }

    /*
  <status>
    <created_at>Fri May 30 17:04:22 +0000 2008</created_at>
    <id>823477057</id>
    <text>double double at in n out on Magnolia</text>
    <source>web</source>
    <truncated>false</truncated>
    <in_reply_to_status_id></in_reply_to_status_id>
    <in_reply_to_user_id></in_reply_to_user_id>
    <favorited>false</favorited>
    <user>
      <id>14500444</id>
      <name>arenson</name>
      <screen_name>arenson</screen_name>
      <location>Texas</location>
      <description>I like girls, Mexican Food, and laughter. </description>
      <profile_image_url>http://s3.amazonaws.com/twitter_production/profile_images/54044033/s7958437_39956964_9393_normal.jpg</profile_image_url>
      <url></url>
      <protected>false</protected>
      <followers_count>12</followers_count>
    </user>
  </status>*/
    @Override
    public int hashCode() {
        return (int) id;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return obj instanceof Status && ((Status) obj).id == this.id;
    }

    @Override
    public String toString() {
        return "Status{" +
                "createdAt=" + createdAt +
                ", id=" + id +
                ", text='" + text + '\'' +
                ", source='" + source + '\'' +
                ", isTruncated=" + isTruncated +
                ", inReplyToStatusId=" + inReplyToStatusId +
                ", inReplyToUserId=" + inReplyToUserId +
                ", isFavorited=" + isFavorited +
                ", user=" + user +
                '}';
    }
}
