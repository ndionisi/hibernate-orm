/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.envers.query.projection.internal;

import java.util.Map;

import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.envers.internal.entities.EntityInstantiator;
import org.hibernate.envers.internal.tools.Triple;
import org.hibernate.envers.query.projection.AuditProjection;

/**
 * @author Felix Feisst (feisst dot felix at gmail dot com)
 */
public class EntityAuditProjection implements AuditProjection {

	private final boolean distinct;

	public EntityAuditProjection(final boolean distinct) {
		this.distinct = distinct;
	}

	@Override
	public Triple<String, String, Boolean> getData(final EnversService enversService) {
		// no property is selected, instead the whole entity (alias) is selected
		return Triple.make( null, null, distinct );
	}

	@Override
	public Object convertQueryResult(
			final EnversService enversService,
			final EntityInstantiator entityInstantiator,
			final String entityName,
			final Number revision,
			final Object value) {
		final Object result;
		if ( enversService.getEntitiesConfigurations().isVersioned( entityName ) ) {
			result = entityInstantiator.createInstanceFromVersionsEntity( entityName, (Map) value, revision );
		}
		else {
			result = value;
		}
		return result;
	}

}
