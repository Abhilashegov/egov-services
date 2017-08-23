Feature: Creating a Property Tax with Data Entry Screen

  Scenario Outline: Create A DataEntryScreen

    ### On Login Screen ###
    Given user on Login screen verifies signInText has visible value Sign In
    And user on Login screen types on username value mh.narasappa
    And user on Login screen types on password value 12345678
    And user on Login screen clicks on signIn

    ### On Homepage Screen ###
    And user on Home screen verifies myTasks has visible value My Tasks
    And user on Home screen clicks on menu
    And user on Home screen types on menuSearch value Create Data Entry
    And user on Home screen clicks on firstMenuItem

    ### On Create Data Entry Screen entering owner details ###
    And user on PropertyTax screen verifies dataEntryText has visible value Create DataEntry
    And user on PropertyTax screen types on oldPropertyId value <Old Property No>
    And user on PropertyTax screen types on aadhaarNumber value <Aadhaar Number>
    And user on PropertyTax screen types on phoneNumber value <Phone Number>
    And user on PropertyTax screen types on ownerName value <Owner Name>
    And user on PropertyTax screen selects on gender value <Gender>
    And user on PropertyTax screen types on email value <Email>
    And user on PropertyTax screen types on panNumber value <Pan>
    And user on PropertyTax screen selects on guardianRelation value <Guardian Relation>
    And user on PropertyTax screen types on guardian value <Guardian>
    And user on PropertyTax screen types on percentageOfOwnerShip value <Percentage Of OwnerShip>
    And user on PropertyTax screen clicks radio button or checkbox on primaryOwner

    ### On Create Data Entry Screen entering property address details ###
    And user on PropertyTax screen types on referencePropertyNumber value <Reference Property Number>
    And user on PropertyTax screen types on doorNo value <Door No>
    And user on PropertyTax screen selects on locality value <Locality>
    And user on PropertyTax screen selects on electionWard value <Election Ward>
    And user on PropertyTax screen selects on zoneNo value <Zone No>
    And user on PropertyTax screen selects on wardNo value <Ward No>
#    And user on PropertyTax screen selects on blockNo value <Block No>
#    And user on PropertyTax screen selects on street value <Street>
#    And user on PropertyTax screen selects on revenueCircle value <Revenue Circle>
    And user on PropertyTax screen types on pincode value <Pin>
    And user on PropertyTax screen selects on noOfFloors value <Total Floors>

    ### On Create Data Entry Screen entering Assessment details ###
    And user on PropertyTax screen selects on reasonForCreation value <Reason for Creation>
    And user on PropertyTax screen selects on propertyType value <Property Type>
    And user on PropertyTax screen selects on propertyTypeSubType value <Property Sub Type>
    And user on PropertyTax screen selects on usageType value <Usage Type>
    And user on PropertyTax screen selects on assessmentUsageSubType value <Usage Sub Type>
    And user on PropertyTax screen types on totalArea value <Total Area>
    And user on PropertyTax screen types on sequenceNo value <Sequence No>
    And user on PropertyTax screen types on buildingPermissionNumber value <Building permission Number>
    And user on PropertyTax screen selectDate on buildingPermissionDate value <Building Permission Date>

    ### On Create Data Entry Screen entering Property Factors ###
    And user on PropertyTax screen selects on toiletFactor value <Toilet Factor>
    And user on PropertyTax screen selects on raodFactor value <Road Factor>
    And user on PropertyTax screen selects on liftFactor value <Lift Factor>
    And user on PropertyTax screen selects on parkingFactor value <Parking Factor>

    ### On Create Data Entry Screen entering Floor Details ###
    And user on PropertyTax screen selects on floorNo value <Floor No>
    And user on PropertyTax screen selects on unitType value <Unit Type>
    And user on PropertyTax screen types on unitNo value <Unit No>
    And user on PropertyTax screen selects on constructionClass value <Construction Class>
#    And user on PropertyTax screen selects on floorUsageType value <Floor Usage Type>
#    And user on PropertyTax screen selects on floorUsageSubType value <Floor Usage Sub Type>
    And user on PropertyTax screen types on firmName value <Firm Name>
    And user on PropertyTax screen selects on occupancy value <Occupancy>
    And user on PropertyTax screen types on occupantName value <Occupancy Name>
#    And user on PropertyTax screen types on annualRent value <Annual Rent>
    And user on PropertyTax screen types on annualRetableValue value <Annual Retable Value>
    And user on PropertyTax screen types on retableValue value <Retable Value>
    And user on PropertyTax screen selectDate on constructionStartDate value <Construction Start Date>
    And user on PropertyTax screen selectDate on constructionEndDate value <Construction end Date>
    And user on PropertyTax screen selectDate on effectiveFromDate value <Effective from Date>
    And user on PropertyTax screen selects on unStructuredLand value <Unstructured Land>
#    And user on PropertyTax screen types on length value <length>
#    And user on PropertyTax screen types on breadth value <Breadth>
    And user on PropertyTax screen types on buildUpArea value <BuildUp Area>
    And user on PropertyTax screen types on carpetArea value <Carpet Area>
    And user on PropertyTax screen types on exemptedArea value <Exempted area>
    And user on PropertyTax screen types on occupancyCertificateNumber value <Occupany Certificate Number>
    And user on PropertyTax screen types on buildingCost value <Building cost>
    And user on PropertyTax screen types on landCost value <Land Cost>

    ### On Create Data Entry Screen entering Construction Details ###
    And user on PropertyTax screen selectDate on currentAssessmentDate value <Current Assessment date>
    And user on PropertyTax screen selectDate on firstAssessmentDate value <First Assessment Date>
    And user on PropertyTax screen selectDate on revisedAssessmentDate value <Revised Assessment Date>
    And user on PropertyTax screen selectDate on lastAssessmentDate value <Last assessment date>
    And user on PropertyTax screen selectDate on commencementDate value <Commencement date>
    And user on PropertyTax screen types on certificateNumber value <Cerficate number>
    And user on PropertyTax screen selectDate on certificateCompletionDate value <Cerficate Complition Date>
    And user on PropertyTax screen selectDate on certificateReceivedDate value <Cerficate Received Date>
    And user on PropertyTax screen types on agencyName value <Agency name>
    And user on PropertyTax screen types on licenseType value <License Type>
    And user on PropertyTax screen types on licenseNumber value <License Number>


    Examples:
      | Old Property No | Aadhaar Number | Phone Number | Owner Name | Gender | Email           | Pan        | Guardian Relation | Guardian | Percentage Of OwnerShip | Reference Property Number | Apartment Name | Door No | Locality  | Election Ward | Zone No | Ward No   | Block No | Street | Revenue Circle | Pin    | Total Floors | Reason for Creation | Property Type | Property Sub Type | Usage Type  | Usage Sub Type | Total Area | Sequence No | Building permission Number | Building Permission Date | Toilet Factor | Road Factor | Lift Factor | Parking Factor | Floor No     | Unit Type | Unit No | Construction Class | Floor Usage Type | Floor Usage Sub Type | Firm Name | Occupancy | Occupancy Name | Annual Rent | Annual Retable Value | Retable Value | Construction Start Date | Construction end Date | Effective from Date | Unstructured Land | length | Breadth | BuildUp Area | Carpet Area | Exempted area | Occupany Certificate Number | Building cost | Land Cost | Current Assessment date | First Assessment Date | Revised Assessment Date | Last assessment date | Commencement date | Cerficate number | Cerficate Complition Date | Cerficate Received Date | Agency name | License Type | License Number |
      | 1012000001      | 202422314421   | 9967231546   | Sunil      | Male   | sunil@gmail.com | BGDHT4703E | Father            | P Sunil  | 100                     | 101211111111112           | NA             | 10001   | AndharAli | Prabhag 1     | Zone A  | Prabhag 2 | NA       | NA     | NA             | 560037 | 1 Floor      | New Property        | Building      | Options           | Residential | Residential    | 5000       | 1           | RO1                        | 01/08/2017               | 1             | 2           | 3           | 4              | Ground Floor | Room      | 505     | RCC Load bearing   | NON RESIDETIAL   | NON RESIDETIAL       | Firm      | Owner     | Sunil          | 2000        | 2400                 | 200           | 02/08/2017              | 03/08/2017            | 04/08/2017          | Yes               |        |         | 3000         | 500         | 200           | 10                          | 200000        | 50000     | 05/08/2017              | 06/08/2017            | 07/08/2017              | 08/08/2017           | 09/08/2017        | 111              | 10/08/2017                | 11/08/2017              | Raghav      | A1           | 3332           |






