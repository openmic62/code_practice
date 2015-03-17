package com.canon.vi.newsarticlereader.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ArticlesContent {

	/**
	 * An array of sample (Article) items.
	 */
	public static List<Article> ARTICLES = new ArrayList<Article>();

	/**
	 * A map of sample (Article) items, by ID.
	 */
	public static Map<String, Article> ARTICLES_MAP = new HashMap<String, Article>();

	static {
		// Add 3 sample items.
		addItem(new Article("My Life as a Java Architect",
				"My Life as a Java Architect\n\nThis article details my life after Virtual Imaging."));
		addItem(new Article("How to Make $250k a Year Using Java",
				"How to Make $250k a Year Using Java\n\nThis article explains how qigong pours abundance into my life."));
		addItem(new Article(
				"Gratitude",
				"Gratitude\n\nThis article touches on some of the huge benefits I gained while working in the x-ray industry."));
	}

	private static void addItem(Article article) {
		ARTICLES.add(article);
		ARTICLES_MAP.put(article.mHeadline, article);
	}

	/**
	 * An article representing a piece of content.
	 */
	public static class Article {
		private String mHeadline;
		private String mBody;

		public Article(String headline, String article) {
			this.mHeadline = headline;
			this.mBody = article;
		}

		public String getHeadline() {
			return this.mHeadline;
		}

		public String getBody() {
			return this.mBody;
		}

		@Override
		public String toString() {
			return mHeadline;
		}
	}
}
