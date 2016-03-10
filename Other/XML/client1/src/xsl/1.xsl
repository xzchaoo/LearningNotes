<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:strip-space elements="*" />
	<xsl:template match="/">
		<html>
			<head>
				<title>persons~</title>
			</head>
			<body>
				<h2>各种人们</h2>
				<xsl:apply-templates />
			</body>
		</html>
	</xsl:template>
	<xsl:template match="persons">
		<xsl:apply-templates>
			<xsl:sort select="@age" order="ascending" data-type="number" />
		</xsl:apply-templates>
	</xsl:template>
	<xsl:template match="person">
		<xsl:if test="@name='xzc'">
			<div>
				[name=
				<xsl:value-of select="@name" />
				,age=
				<xsl:value-of select="@age" />
				]
				<xsl:value-of select="text()" />
			</div>
		</xsl:if>
	</xsl:template>
	<xsl:template match="student">
		<xsl:choose>
			<xsl:when test="@name='xzc'">
				xzc
			</xsl:when>
			<xsl:otherwise>
				<div>
					[name=
					<xsl:value-of select="@name" />
					,age=
					<xsl:value-of select="@age" />
					,grade=
					<xsl:value-of select="grade/text()" />
					]
				</div>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet>