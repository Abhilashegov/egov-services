delete from eg_roleaction where roleCode='CITIZEN' and actionid=(select id from eg_action where name='Get all ReceivingMode');
delete from eg_roleaction where roleCode='EMPLOYEE' and actionid=(select id from eg_action where name='Get all ReceivingMode');
delete from eg_roleaction where roleCode='SUPERUSER' and actionid=(select id from eg_action where name='Get all ReceivingMode');
delete from eg_roleaction where roleCode='GO' and actionid=(select id from eg_action where name='Get all ReceivingMode');
delete from eg_roleaction where roleCode='RO' and actionid=(select id from eg_action where name='Get all ReceivingMode');
delete from eg_roleaction where roleCode='GA' and actionid=(select id from eg_action where name='Get all ReceivingMode');
delete from eg_roleaction where roleCode='GRO' and actionid=(select id from eg_action where name='Get all ReceivingMode');
delete from eg_roleaction where roleCode='CITIZEN' and actionid=(select id from eg_action where name='Get all ReceivingCenters');
delete from eg_roleaction where roleCode='EMPLOYEE' and actionid=(select id from eg_action where name='Get all ReceivingCenters');
delete from eg_roleaction where roleCode='SUPERUSER' and actionid=(select id from eg_action where name='Get all ReceivingCenters');
delete from eg_roleaction where roleCode='GO' and actionid=(select id from eg_action where name='Get all ReceivingCenters');
delete from eg_roleaction where roleCode='RO' and actionid=(select id from eg_action where name='Get all ReceivingCenters');
delete from eg_roleaction where roleCode='GA' and actionid=(select id from eg_action where name='Get all ReceivingCenters');
delete from eg_roleaction where roleCode='GRO' and actionid=(select id from eg_action where name='Get all ReceivingCenters');
