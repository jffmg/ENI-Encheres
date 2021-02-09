package fr.eni.ecole.trocenchere.bo;

import java.time.LocalDateTime;

public class Bid {

	int userId;
	int articleId;
	LocalDateTime bidDate;
	int valueBid;
	
	public Bid(int userId, int articleId, LocalDateTime bidDate, int valueBid) {
		super();
		this.userId = userId;
		this.articleId = articleId;
		this.bidDate = bidDate;
		this.valueBid = valueBid;
	}

	public Bid(int userId, int articleId, int valueBid) {
		super();
		this.userId = userId;
		this.articleId = articleId;
		this.valueBid = valueBid;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public LocalDateTime getBidDate() {
		return bidDate;
	}

	public void setBidDate(LocalDateTime bidDate) {
		this.bidDate = bidDate;
	}

	public int getValueBid() {
		return valueBid;
	}

	public void setValueBid(int valueBid) {
		this.valueBid = valueBid;
	}
	
	
}
