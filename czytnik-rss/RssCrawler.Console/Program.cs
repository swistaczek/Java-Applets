using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Media;

/* Prosty czytnik RSS - C# demo */
namespace RssCrawler.Console
{
	class Program
	{
		static void Main(string[] args)
		{
			string[] feeds = new []
			{
				"http://rss.feedsportal.com/c/32739/f/530278/index.rss",
				"http://gazeta.pl.feedsportal.com/c/32739/f/576250/index.rss",
				"http://rss.gazeta.pl/pub/rss/kotek.xml"
			};

            System.Console.WriteLine("Rozpoczynam pobieranie źródeł ({0})", feeds.Length);
            System.Console.WriteLine("Czekaj cierpliwie");
            System.Console.WriteLine("------------------------------------------------------------------");

			Runner.FeedRunner runner = new Runner.FeedRunner();
			runner.Crawl(feeds);

			System.Console.ReadLine();
		}
	}
}