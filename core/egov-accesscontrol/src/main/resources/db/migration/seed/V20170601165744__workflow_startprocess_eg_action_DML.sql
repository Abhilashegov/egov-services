insert into eg_action (ID,servicecode,NAME,URL,QUERYPARAMS,PARENTMODULE,ORDERNUMBER,DISPLAYNAME,ENABLED,CREATEDBY,CREATEDDATE,LASTMODIFIEDBY,LASTMODIFIEDDATE) values (NEXTVAL('SEQ_EG_ACTION'),'processstart','processstart',
'/process/_start',null,(select id from service where name='Workflow_MS'),1,'processstart',false,1,now(),1,now());
insert into eg_roleaction (rolecode, actionid, tenantid) values('SUPERUSER', (Select id from eg_action where url='/process/_start'), 'default');
