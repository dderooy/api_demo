import json
import urllib2
from collections import namedtuple


def _json_object_hook(d): return namedtuple('X', d.keys())(*d.values())
def json2obj(data): return json.loads(data, object_hook=_json_object_hook)
def getJsonResponse(substr, page):
    contents = urllib2.urlopen("https://jsonmock.hackerrank.com/api/movies/search/?Title=" + substr +"&page=" + str(page)).read()
    return json2obj(contents)

def getMovieTitles(substr):
    pageOne = getJsonResponse(substr, 1)
    data = pageOne.data
    titles=[]
    for x in data:
        titles.append(x.Title)
    if pageOne.total_pages > 1:
        for i in range(2, pageOne.total_pages+1):
            extraPage = getJsonResponse(substr, i)
            data = extraPage.data
            for x in data:
                titles.append(x.Title)
    titles.sort()
    return titles

#Enter the desired search term below
titles = getMovieTitles("spiderman")
for x in titles:
    print x