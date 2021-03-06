/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.legacy.jnp.infinispan;

import java.util.List;
import org.wildfly.clustering.group.Group;
import org.wildfly.clustering.group.Node;
import org.jboss.legacy.jnp.infinispan.ClusterListener;
/**
 *
 * @author <a href="mailto:ehugonne@redhat.com">Emmanuel Hugonnet</a> (c) 2013 Red Hat, inc.
 * @author <a href="mailto:bbaranow@redhat.com">baranowb</a> (c) 2013 Red Hat, inc.
 */
public class GroupMembershipListenerAdapter implements Group.Listener {

    private final ClusterListener listener;

    public GroupMembershipListenerAdapter(ClusterListener listener) {
        this.listener = listener;
    }

    /* (non-Javadoc)
     * @see org.wildfly.clustering.group.Group.Listener#membershipChanged(java.util.List, java.util.List, boolean)
     */
    @Override
    public void membershipChanged(List<Node> previousMembers, List<Node> members, boolean merged) {
        //TODO: XXX
        if(merged){
            
        } else {
            
        }
    }

}
