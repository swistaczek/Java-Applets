using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Threading;
using System.Xml;
using System.Xml.Linq;

/* Prosty czytnik RSS - C# demo */
/* Worker odpowiedzialny za pobieranie feed�w, w�tek */
namespace RssCrawler.Runner
{
	public class FeedRunner
	{
		public int ArticleCount { get; set; }
		private Mutex _mutex = new Mutex();

        /* Metoda publiczna do processingu feedu */
		public void Crawl(string[] feeds)
		{
			foreach (var feed in feeds)
			{
				var feedFetcher = new FeedFetcher(feed, this);
				Console.WriteLine("Pobieranie: {0} - zaczekaj", feed);
				Console.ReadLine();
				new Thread(feedFetcher.ProcessFeed).Start();
			}
		}


        /* Metoda publiczna do zwi�kszania ilo�ci artyku��w */
		public void UpdateCount()
		{
			lock(_mutex)
			{
				ArticleCount++;
			}
		}
	}

    /* Klasa artyku�u */
	public class Article
	{
		public string Title { get; set; }
		public string Link { get; set; }
		public string Description { get; set; }
	}
    
    /* Klasa workera pobieraj�cego feed */
	public class FeedFetcher
	{
		private FeedRunner Runner { get; set; }
		private readonly string _feedUrl;

		public FeedFetcher(string feedUrl, FeedRunner runner)
		{
			Runner = runner;
			_feedUrl = feedUrl;
		}
        
        /* Wykorzystywanie WebRequest i obrabianie pliku */
		public void ProcessFeed()
		{
			WebRequest request = WebRequest.Create(_feedUrl);
			using (var response = request.GetResponse())
			{
				var xmlReader = XmlReader.Create(response.GetResponseStream());
				XDocument document = XDocument.Load(xmlReader);

				IEnumerable<Article> articles = document.Descendants("channel")
					.Elements("item")
					.Select(i => new Article { Title = i.Element("title").Value, Link = i.Element("link").Value});

                /* Wy�wietlanie w p�tli wszystkich artyku��w - boskie foreach! */
				foreach (var article in articles)
				{
					Runner.UpdateCount();
					Console.WriteLine("#{0}  Artyku�: {1}", Runner.ArticleCount, article.Title);
				}
			}
		}

	}
}