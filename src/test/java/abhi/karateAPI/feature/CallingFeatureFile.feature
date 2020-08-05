Feature: which makes a 'call' to another re-usable feature

  #Scenario:
  #* def genrateUTKN = call read('classpath:GenUTKN.feature') { mobileNumber: '9818533677', methodType: 'GET', urlPath: 'https://content.airtel.tv/app/v1/content' }
  #* def authToken = genrateUTKN.auth
  #* print 'Calling UTKN---' authToken
  Scenario: calling args
    #* def mobileNumber = { mobN: '9876543210' }
    #* def methodType = {methodt: 'GET'}
    #* def urlPath = {urlP: 'https://content.airtel.tv/app/v1/content'}
    * def result = call read('GenUTKN.feature')
    * print 'Calling UTKN---' result
