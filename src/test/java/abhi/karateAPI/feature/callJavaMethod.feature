Feature: Java Function
	
  Scenario: Call Java Function
    * def mob = '9818533677'
    * def method = 'POST'
    * def urlx = 'https://content.airtel.tv/app/v1/config/appConfig'
    
    * def auth =
      """
      function(mobNumber,methodType,apiUrl){
        var tvUtkn = Java.type('abhi.karateAPI.utils.TvUtknGenerator');
        var obj = new tvUtkn();
      return obj.getUtkn(mobNumber,methodType,apiUrl);
      }
      """
    * string utkn = auth(mob, method, urlx)
    * print 'UTKN--', utkn
    
    * def uidm =
      """
      function(mobNum){
        var uID = Java.type('abhi.karateAPI.utils.GenerateUid');
        var obj1 = new uID();
      return obj1.getUidMsisdn(mobNum);
      }
      """
    * string uId = uidm(mob)
    * print 'UID--', uId
