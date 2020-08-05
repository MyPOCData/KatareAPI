@tag
Feature: Create UTKN for different Mobile Numbers

  @tag2
  Scenario Outline: Genrate UTKN
    * def auth =
      """
      function(mobNumber,methodType,apiUrl){
        var tvUtkn = Java.type('abhi.karateAPI.utils.TvUtknGenerator');
        var obj = new tvUtkn();
      return obj.getUtkn(mobNumber,methodType,apiUrl);
      }
      """
    * string utkn = auth(<mob>,<methodType>,<urlx>)
    * print 'UTKN--', utkn
    * def contentResponse = read('../responses/contentResponse.json')
    * def headerData = 	{ x-atv-utkn: '#(utkn)', content-type: 'application/json' }
    Given url <urlx>
    And param id = <contentID>
    And headers headerData
    When method GET
    Then status 200
    Then print response
    Then print headerData
    #    And match response == contentResponse
    And match response.playableId == 'HOICHOI_EPISODE_2957'

    Examples: 
      | mob          | methodType | urlx                                       | contentID             |
      | '9818533677' | 'GET'      | 'https://content.airtel.tv/app/v1/content' | 'HOICHOI_TVSHOW_2955' |
