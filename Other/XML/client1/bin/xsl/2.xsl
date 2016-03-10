<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<html>
			<body>
				a=
				<xsl:value-of select="$a" />
				<xsl:apply-templates>
					<xsl:with-param name="a" select="5" />
				</xsl:apply-templates>
				a=
				<xsl:value-of select="$a" />
			</body>
		</html>
	</xsl:template>
	<xsl:template match="persons">
		<xsl:param name="a" select="4" />
		a=
		<xsl:value-of select="$a" />
		<xsl:apply-templates />
	</xsl:template>
	<xsl:template match="person">
		<!--
			<xsl:copy />
			<xsl:copy-of select="." />
			<xsl:number format="I" />

			<div>
			____
			<xsl:number grouping-separator="," grouping-size="1" level="multiple" count="persons | person" />
			</div>
			<br /> -->
	</xsl:template>
	<xsl:param name="a" select="4" />
</xsl:stylesheet>