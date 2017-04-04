package com.jcourse.kladov;

import lombok.extern.log4j.Log4j;
import org.jsoup.Jsoup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Log4j
public class Main {
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Usage: documentIndexer <file_name>");
			return;
		}

		try {
			Indexer indexer = new Indexer(new Metric[]{new WordCounter()});
			indexer.processDocument(new TextDocument(Jsoup.connect(args[0]).get().text()));
			indexer.serializeMetrics(new CSVSerializer());
		} catch (FileNotFoundException e) {
			log.warn("File not found " + args[0], e);
		} catch (IOException e) {
			log.warn("IOException " + args[0], e);
		}
	}
}
