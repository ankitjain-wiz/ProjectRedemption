package test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Comment", propOrder = { "commentOne", "commentTwo" })
public class Comment {
	@XmlElement(name = "CommentOne")
	protected String commentOne;
	@XmlElement(name = "CommentTwo")
	protected String commentTwo;

	public String getCommentOne() {
		return commentOne;
	}

	public void setCommentOne(String commentOne) {
		this.commentOne = commentOne;
	}

	public String getCommentTwo() {
		return commentTwo;
	}

	public void setCommentTwo(String commentTwo) {
		this.commentTwo = commentTwo;
	}

	@Override
	public String toString() {
		return "Comment [commentOne=" + commentOne + ", commentTwo=" + commentTwo + "]";
	}
	
	

}