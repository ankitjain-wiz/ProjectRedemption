SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO


IF EXISTS(SELECT 1 FROM sys.columns WHERE Name = N'FilingLanguage' AND Object_ID = Object_ID(N'[CommonService].[Application]'))
BEGIN
    ALTER TABLE [CommonService].[Application] ALTER COLUMN FilingLanguage VARCHAR(3) NULL
END
GO


IF EXISTS(SELECT 1 FROM sys.columns WHERE Name = N'ReceiptDatetime' AND Object_ID = Object_ID(N'[CommonService].[Application]'))
BEGIN
    ALTER TABLE [CommonService].[Application] ALTER COLUMN ReceiptDatetime DATETIME2(0) NULL
END
GO


SET ANSI_PADDING OFF
GO







