var dat = {
  "legal.create": {
    numCols: 4,
    title:"advocates.search.document.title",
    useTimestamp: true,
    objectName: "agencies",
    groups: [
       {
        name: "agencyDetails",
        label: "advocates.create.group.title.agencyDetails",
        fields: [
         {
            name: "advocateTitle",
            jsonPath: "title",
            label: "advocates.create.advocateTitle",
            type: "singleValueList",
            isRequired: false,
            isDisabled: false,
            patternErrorMsg: "",
            defaultValue:[{ key:"Mr",value:"Mr"},{ key:"Mrs",value:"Mrs"},{ key:"Ms",value:"Ms"},{ key:"Miss",value:"Miss"}]
          },
          {
            name: "firstName",
            jsonPath: "firstName",
            label: "advocates.create.firstName",
            pattern: "",
            type: "text",
            isRequired: true,
            isDisabled: false,
            requiredErrMsg: "",
            patternErrMsg: ""
          },
          {
            name: "secondName",
            jsonPath: "secondName",
            label: "advocates.create.secondName",
            pattern: "",
            type: "text",
            isRequired: true,
            isDisabled: false,
            requiredErrMsg: "",
            patternErrMsg: ""
          },
          {
            name: "lastName",
            jsonPath: "lastName",
            label: "advocates.create.lastName",
            pattern: "",
            type: "text",
            isRequired: true,
            isDisabled: false,
            requiredErrMsg: "",
            patternErrMsg: ""
          },
          {
            name: "aadharNumber",
            jsonPath: "aadhar",
            label: "advocates.create.aadharNumber",
            pattern: "",
            type: "aadhar",
            isRequired: true,
            isDisabled: false,
            requiredErrMsg: "",
            patternErrMsg: " The UID number is as follows:412521475850 : all 12 digits"
          },
          {
            name: "gender",
            jsonPath: "gender",
            label: "advocates.create.gender",
            pattern: "",
            type: "singleValueList",
            isRequired: true,
            isDisabled: false,
            requiredErrMsg: "",
            patternErrMsg: "",
             defaultValue:[{ key:"Male",value:"Male"},{ key:"Female",value:"Female"}]
          },
          {
            name: "age",
            jsonPath: "age",
            label: "advocates.create.age",
            pattern: "",
            type: "number",
            isRequired: true,
            isDisabled: false,
            requiredErrMsg: "",
            patternErrMsg: ""
          },
          {
            name: "dob",
            jsonPath: "dob",
            label: "advocates.create.dob",
            pattern: "",
            type: "datePicker",
            isRequired: true,
            isDisabled: false,
            requiredErrMsg: "",
            patternErrMsg: ""
          },
          {
            name: "address",
            jsonPath: "address",
            label: "advocates.create.address",
            pattern: "",
            type: "textarea",
            isRequired: true,
            isDisabled: false,
            requiredErrMsg: "",
            patternErrMsg: ""
          },
          {
            name: "mobileNumber",
            jsonPath: "mobileNumber",
            label: "advocates.create.mobileNumber",
            pattern: "",
            type: "mobileNumber",
            isRequired: true,
            isDisabled: false,
            requiredErrMsg: "",
            patternErrMsg: "The Phone number structure is as follows: 999XXXX999 "
          },
          {
            name: "contactNumber",
            jsonPath: "contactNo",
            label: "advocates.create.contactNumber",
            pattern: "",
            type: "mobileNumber",
            isRequired: true,
            isDisabled: false,
            requiredErrMsg: "",
            patternErrMsg: "The Phone number structure is as follows: 999XXXX999 "
          },
          {
            name: "email",
            jsonPath: "emailId",
            label: "advocates.create.email",
            pattern: "",
            type: "email",
            isRequired: true,
            isDisabled: false,
            requiredErrMsg: "",
            patternErrMsg: " Email should be in format e.g - abc@abc.com"
          },
          {
            name: "panNumber",
            jsonPath: "pan",
            label: "advocates.create.panNumber",
            pattern: "",
            type: "pan",
            isRequired: true,
            isDisabled: false,
            requiredErrMsg: "",
            patternErrMsg: " The PAN structure is as follows: AAAPL1234C ."
          },
          {
            name: "VATTinNumber",
            jsonPath: "vatTinNo",
            label: "advocates.create.VATTinNumber",
            pattern: "",
            type: "text",
            maxLength:"15",
            isRequired: false,
            isDisabled: false,
            requiredErrMsg: "",
            patternErrMsg: ""
          }
        ]
      }
    ]
  }

};
export default dat;
