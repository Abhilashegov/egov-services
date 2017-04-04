insert into eg_action(id, name,url,servicecode,queryparams,parentmodule,ordernumber,displayname,enabled,createdby,createddate,lastmodifiedby,lastmodifieddate,tenantId)
values(nextval('SEQ_EG_ACTION'),'Get all ReceivingMode','/pgr/receivingmode','PGR','tenantId=',(select code from service where name ='PGR' and contextroot='pgr'),null,'Get all ReceivingMode',false,1,now(),1,now(),'ap.kurnool');
insert into eg_action(id, name,url,servicecode,queryparams,parentmodule,ordernumber,displayname,enabled,createdby,createddate,lastmodifiedby,lastmodifieddate,tenantId)
values(nextval('SEQ_EG_ACTION'),'Get all CompaintTypeCategory','/pgr/complaintTypeCategories','PGR','tenantId=',(select code from service where name ='PGR' and contextroot='pgr'),null,'Get all CompaintTypeCategory',false,1,now(),1,now(),'ap.kurnool');
insert into eg_action(id, name,url,servicecode,queryparams,parentmodule,ordernumber,displayname,enabled,createdby,createddate,lastmodifiedby,lastmodifieddate,tenantId)
values(nextval('SEQ_EG_ACTION'),'Get ComplaintType by type,count and tenantId','/pgr/services','PGR','type=&count=&tenantId=',(select code from service where name ='PGR' and contextroot='pgr'),null,'Get ComplaintType by type,count and tenantId',false,1,now(),1,now(),'ap.kurnool');
insert into eg_action(id, name,url,servicecode,queryparams,parentmodule,ordernumber,displayname,enabled,createdby,createddate,lastmodifiedby,lastmodifieddate,tenantId)
values(nextval('SEQ_EG_ACTION'),'Get all ReceivingCenters','/pgr/receivingcenter','PGR','tenantId=',(select code from service where name ='PGR' and contextroot='pgr'),null,'Get all ReceivingCenters',false,1,now(),1,now(),'ap.kurnool');
insert into eg_action(id, name,url,servicecode,queryparams,parentmodule,ordernumber,displayname,enabled,createdby,createddate,lastmodifiedby,lastmodifieddate,tenantId)
values(nextval('SEQ_EG_ACTION'),'Get ComplaintType by type,categoryId and tenantId','/pgr/services','PGR','type=&categoryId=&tenantId=',(select code from service where name ='PGR' and contextroot='pgr'),null,'Get ComplaintType by type,categoryId and tenantId',false,1,now(),1,now(),'ap.kurnool');
insert into eg_action(id, name,url,servicecode,queryparams,parentmodule,ordernumber,displayname,enabled,createdby,createddate,lastmodifiedby,lastmodifieddate,tenantId)
values(nextval('SEQ_EG_ACTION'),'Get location by LocationName','/v1/location/boundarys/getLocationByLocationName','PGR','locationName=',(select code from service where name='PGR'and contextroot='pgr'),null,'Get location by LocationName',false,1,now(),1,now(),'ap.kurnool');
insert into eg_action(id, name,url,servicecode,queryparams,parentmodule,ordernumber,displayname,enabled,createdby,createddate,lastmodifiedby,lastmodifieddate,tenantId)
values(nextval('SEQ_EG_ACTION'),'Search Boundary by BoundryTypeName and HierarchyTypeName','/v1/location/boundarys/boundariesByBndryTypeNameAndHierarchyTypeName','PGR','boundaryTypeName=&hierarchyTypeName=',(select code from service where name ='PGR' and contextroot='pgr'),null,'Search Boundary',false,1,now(),1,now(),'ap.kurnool');
insert into eg_action(id, name,url,servicecode,queryparams,parentmodule,ordernumber,displayname,enabled,createdby,createddate,lastmodifiedby,lastmodifieddate,tenantId)
values(nextval('SEQ_EG_ACTION'),'Get ComplaintType by type and tenantId','/pgr/services','PGR','type=&tenantId=',(select code from service where name ='PGR' and contextroot='pgr'),null,'Get ComplaintType by type and tenantId',false,1,now(),1,now(),'ap.kurnool');
insert into eg_action(id, name,url,servicecode,queryparams,parentmodule,ordernumber,displayname,enabled,createdby,createddate,lastmodifiedby,lastmodifieddate,tenantId)
values(nextval('SEQ_EG_ACTION'),'Get all Statuses','/pgr/_statuses','PGR','tenantId=',(select code from service where name='PGR'and contextroot='pgr'),null,'Get all Statuses',false,1,now(),1,now(),'ap.kurnool');
insert into eg_action(id, name,url,servicecode,queryparams,parentmodule,ordernumber,displayname,enabled,createdby,createddate,lastmodifiedby,lastmodifieddate,tenantId)
values(nextval('SEQ_EG_ACTION'),'Get next statuses by CurrentStatus and Role','/pgr/_getnextstatuses','PGR','userId=&currentStatus=&tenantId=',(select code from service where name='PGR'and contextroot='pgr'),null,'Get next statuses by CurrentStatus and Role',false,1,now(),1,now(),'ap.kurnool');
insert into eg_action(id, name,url,servicecode,queryparams,parentmodule,ordernumber,displayname,enabled,createdby,createddate,lastmodifiedby,lastmodifieddate,tenantId)
values(nextval('SEQ_EG_ACTION'),'Get Workflow History','/workflow/history','PGR','tenantId=&workflowId=',(select code from service where name='PGR'and contextroot='pgr'),null,'Get Workflow History',false,1,now(),1,now(),'ap.kurnool');
insert into eg_action(id, name,url,servicecode,queryparams,parentmodule,ordernumber,displayname,enabled,createdby,createddate,lastmodifiedby,lastmodifieddate,tenantId)
values(nextval('SEQ_EG_ACTION'),'Get ChildBoundary by BoundaryId','/v1/location/boundarys/childLocationsByBoundaryId','PGR','boundaryId=',(select code from service where name='PGR' and contextroot='pgr'),null,'Get ChildBoundary by BoundaryId',false,1,now(),1,now(),'ap.kurnool');
insert into eg_action(id, name,url,servicecode,queryparams,parentmodule,ordernumber,displayname,enabled,createdby,createddate,lastmodifiedby,lastmodifieddate,tenantId)
values(nextval('SEQ_EG_ACTION'),'Get Department by code and id','/eis/departments','PGR','',(select code from service where name='PGR'and contextroot='pgr'),null,'Get Department by code and id',false,1,now(),1,now(),'ap.kurnool');
insert into eg_action(id, name,url,servicecode,queryparams,parentmodule,ordernumber,displayname,enabled,createdby,createddate,lastmodifiedby,lastmodifieddate,tenantId)
values(nextval('SEQ_EG_ACTION'),'Get File by FileStoreId','/filestore/v1/files/id','PGR','fileStoreId=',(select code from service where name='PGR'and contextroot='pgr'),null,'Get File by FileStoreId',false,1,now(),1,now(),'ap.kurnool');
insert into eg_action(id, name,url,servicecode,queryparams,parentmodule,ordernumber,displayname,enabled,createdby,createddate,lastmodifiedby,lastmodifieddate,tenantId)
values(nextval('SEQ_EG_ACTION'),'Get Department by DepartmentId','/eis/designationByDepartmentId','PGR','id=',(select code from service where name='PGR'and contextroot='pgr'),null,'Get Department by DepartmentId',false,1,now(),1,now(),'ap.kurnool');
insert into eg_action(id, name,url,servicecode,queryparams,parentmodule,ordernumber,displayname,enabled,createdby,createddate,lastmodifiedby,lastmodifieddate,tenantId)
values(nextval('SEQ_EG_ACTION'),'Get Assignments by DepartmentId Or DesignationId','/eis/assignmentsByDeptOrDesignId','PGR','deptId=&desgnId=',(select code from service where name='PGR'and contextroot='pgr'),null,'Get Assignments by DepartmentId Or DesignationId',false,1,now(),1,now(),'ap.kurnool');
insert into eg_action(id, name,url,servicecode,queryparams,parentmodule,ordernumber,displayname,enabled,createdby,createddate,lastmodifiedby,lastmodifieddate,tenantId)
values(nextval('SEQ_EG_ACTION'),'Seva','/pgr/seva','PGR','',(select id from service where name='PGR'and contextroot='pgr'),null,'Seva',false,1,now(),1,now(),'ap.kurnool');
insert into eg_action(id, name,url,servicecode,queryparams,parentmodule,ordernumber,displayname,enabled,createdby,createddate,lastmodifiedby,lastmodifieddate,tenantId)
values(nextval('SEQ_EG_ACTION'),'Search Boundary by boundaryId','/v1/location/boundarys','PGR','boundary=',(select code from service where name='PGR'and contextroot='pgr'),null,'Search Boundary by boundaryId',false,1,now(),1,now(),'ap.kurnool');
insert into eg_action(id, name,url,servicecode,queryparams,parentmodule,ordernumber,displayname,enabled,createdby,createddate,lastmodifiedby,lastmodifieddate,tenantId)
values(nextval('SEQ_EG_ACTION'),'Complaint Registration','/templates/create-complaint.html','PgrComp','',(select code from service where name='PGRComplaints'and contextroot=''),2,'Register Grievance',true,1,now(),1,now(),'ap.kurnool');
insert into eg_action(id, name,url,servicecode,queryparams,parentmodule,ordernumber,displayname,enabled,createdby,createddate,lastmodifiedby,lastmodifieddate,tenantId)
values(nextval('SEQ_EG_ACTION'),'SearchComplaintFormOfficial','/templates/search-complaint.html','PgrComp','',(select code from service where name='PGRComplaints'and contextroot=''),1,'Search Grievance',true,1,now(),1,now(),'ap.kurnool');
insert into eg_action(id, name,url,servicecode,queryparams,parentmodule,ordernumber,displayname,enabled,createdby,createddate,lastmodifiedby,lastmodifieddate,tenantId)
values(nextval('SEQ_EG_ACTION'),'UpdateComplaintForm','/templates/view-complaint.html','PGR','srn=',(select code from service where name='PGR'and contextroot='pgr'),null,'Update Grievance',false,1,now(),1,now(),'ap.kurnool');