SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

IF NOT EXISTS(SELECT *
              FROM sys.schemas
              WHERE name = N'CommonService')
  EXEC ('CREATE SCHEMA [CommonService]');
GO

/***********************************************************************
* Name           : Application
* Date           : 02-05-2017
* Author         : Ashok Sharma
************************************************************************
* Date      Developer            Change
----------- -------------------- ---------------------------------------
***********************************************************************/
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[CommonService].[Application]') AND type in (N'U'))
BEGIN
CREATE TABLE [CommonService].[Application](
	[ApplicationId] [numeric](15, 0) IDENTITY(1,1) NOT NULL,
	[ApplicationNumber] [varchar](20) NOT NULL,
	[ApplicationType] [varchar](20) NOT NULL,
	[ReceiptDatetime] [datetime2](0) NOT NULL,
	[FilingDatetime] [datetime2](0) NOT NULL,
	[PublicationNumber] [varchar](20) NULL,
	[FilingLanguage] [varchar](3) NOT NULL,
	[ProceduralLanguage] [varchar](3) NULL,
	[FirstAbstractFigure] [numeric](3) NULL,
 CONSTRAINT [PK_Application] PRIMARY KEY CLUSTERED 
(
	[ApplicationId] ASC
))
END
GO

/***********************************************************************
* Name           : CMSCaseMaster
* Date           : 02-05-2017
* Author         : Ashok Sharma
************************************************************************
* Date      Developer            Change
----------- -------------------- ---------------------------------------
***********************************************************************/
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[CommonService].[CMSCaseMaster]') AND type in (N'U'))
BEGIN
CREATE TABLE [CommonService].[CMSCaseMaster](
	[CMSCaseId] [numeric](20, 0) IDENTITY(1,1) NOT NULL,
	[ApplicationId] [numeric](15, 0) NULL,
	[ProductOrderId] [numeric](20, 0) NOT NULL,
 CONSTRAINT [PK_CMSCaseMaster] PRIMARY KEY CLUSTERED 
(
	[CMSCaseId] ASC
))
END
GO


/***********************************************************************
* Name           : ProductOrder
* Date           : 02-05-2017
* Author         : Ashok Sharma
************************************************************************
* Date      Developer            Change
----------- -------------------- ---------------------------------------
***********************************************************************/
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[CommonService].[ProductOrder]') AND type in (N'U'))
BEGIN
CREATE TABLE [CommonService].[ProductOrder](
	[ProductOrderId] [numeric](20, 0) IDENTITY(1,1) NOT NULL,
	[DossierNumber] [varchar](20) NULL,
	[Phase] [varchar](10) NOT NULL,
	[ProductType] [varchar](20) NULL,
	[ApplicationId] [numeric](15, 0) NULL,
	[TaskId] [varchar](50) NULL,
 CONSTRAINT [PK_ProductOrder] PRIMARY KEY CLUSTERED 
(
	[ProductOrderId] ASC
))
END
GO

/***********************************************************************
* Name           : Role
* Date           : 02-05-2017
* Author         : Ashok Sharma
************************************************************************
* Date      Developer            Change
----------- -------------------- ---------------------------------------
***********************************************************************/
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[CommonService].[Role]') AND type in (N'U'))
BEGIN
CREATE TABLE [CommonService].[Role](
	[RoleId] [numeric](3, 0) IDENTITY(1,1) NOT NULL,
	[RoleName] [varchar](16) NOT NULL,
	[Description] [varchar](255) NOT NULL,
 CONSTRAINT [PK_Role] PRIMARY KEY CLUSTERED 
(
	[RoleId] ASC
))
END
GO

IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[CommonService].[FK_CMSCaseMaster_Application_ApplicationId]') AND parent_object_id = OBJECT_ID(N'[CommonService].[CMSCaseMaster]'))
ALTER TABLE [CommonService].[CMSCaseMaster]  ADD CONSTRAINT [FK_CMSCaseMaster_Application_ApplicationId] FOREIGN KEY([ApplicationId])
REFERENCES [CommonService].[Application] ([ApplicationId])
GO

IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[CommonService].[FK_CMSCaseMaster_ProductOrder_ProductOrderId]') AND parent_object_id = OBJECT_ID(N'[CommonService].[CMSCaseMaster]'))
ALTER TABLE [CommonService].[CMSCaseMaster]  ADD CONSTRAINT [FK_CMSCaseMaster_ProductOrder_ProductOrderId] FOREIGN KEY([ProductOrderId])
REFERENCES [CommonService].[ProductOrder] ([ProductOrderId])
GO


IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[CommonService].[FK_ProductOrder_ApplicationId_ApplicationId]') AND parent_object_id = OBJECT_ID(N'[CommonService].[ProductOrder]'))
ALTER TABLE [CommonService].[ProductOrder]  ADD CONSTRAINT [FK_ProductOrder_ApplicationId_ApplicationId] FOREIGN KEY([ApplicationId])
REFERENCES [CommonService].[Application] ([ApplicationId])
GO

IF NOT EXISTS (SELECT name from sys.indexes WHERE name = N'IDX_Application_ApplicationNumber')
BEGIN
CREATE UNIQUE INDEX IDX_Application_ApplicationNumber ON [CommonService].[Application](ApplicationNumber)
END
GO

IF NOT EXISTS (SELECT name from sys.indexes WHERE name = N'IDX_ProductOrder_DossierNumber')
BEGIN
CREATE INDEX IDX_ProductOrder_DossierNumber ON [CommonService].[ProductOrder](DossierNumber)
END
GO

SET ANSI_PADDING OFF
GO
