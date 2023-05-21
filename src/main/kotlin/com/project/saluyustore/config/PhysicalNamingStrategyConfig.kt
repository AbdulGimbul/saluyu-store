package com.project.saluyustore.config

import org.hibernate.boot.model.naming.Identifier
import org.hibernate.boot.model.naming.PhysicalNamingStrategy
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment
import org.springframework.context.annotation.Configuration

@Configuration
class PhysicalNamingStrategyConfig : PhysicalNamingStrategy {
    override fun toPhysicalCatalogName(logicalName: Identifier?, jdbcEnvironment: JdbcEnvironment?): Identifier? {
        return convertToDoubleQuoted(logicalName)
    }

    override fun toPhysicalSchemaName(logicalName: Identifier?, jdbcEnvironment: JdbcEnvironment?): Identifier? {
        return convertToDoubleQuoted(logicalName)
    }

    override fun toPhysicalTableName(logicalName: Identifier?, jdbcEnvironment: JdbcEnvironment?): Identifier? {
        return convertToDoubleQuoted(logicalName)
    }

    override fun toPhysicalSequenceName(logicalName: Identifier?, jdbcEnvironment: JdbcEnvironment?): Identifier? {
        return convertToDoubleQuoted(logicalName)
    }

    override fun toPhysicalColumnName(logicalName: Identifier?, jdbcEnvironment: JdbcEnvironment?): Identifier? {
        return convertToDoubleQuoted(logicalName)
    }

    private fun convertToDoubleQuoted(identifier: Identifier?): Identifier? {
        var newName = identifier?.text
        if (newName!!.matches(".*[A-Z].*".toRegex())) {
            newName = "\u0022" + identifier?.text + "\u0022"
        }
        return identifier!!.isQuoted.let { Identifier.toIdentifier(newName, it) }
    }
}