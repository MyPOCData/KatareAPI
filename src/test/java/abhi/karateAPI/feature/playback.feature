Feature: PlayBack API

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
    
    * def headerData = read('../requestData/playbackHeaders.json')
    * def paramsData = read('../requestData/playbackParam.json')
    
    * print '****************  ',headerData
    
    Given url <urlx>
    And params paramsData
    And headers headerData
    When method GET
    Then status 200
    Then print response

    
    Examples: 
      | mob          | methodType | urlx                                      			 |
      | '9818533677' | 'GET'      | 'https://play-preprod.wynk.in/v4/user/playback'  | 

    