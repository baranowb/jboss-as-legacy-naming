/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
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
package org.jboss.legacy.jnp.connector.simple;

import org.jboss.as.network.SocketBinding;
import org.jboss.legacy.jnp.JNPLogger;
import org.jboss.legacy.jnp.JNPMessages;
import org.jboss.legacy.jnp.connector.JNPServerNamingConnectorService;
import org.jboss.legacy.jnp.server.JNPServer;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;
import org.jboss.msc.value.InjectedValue;

/**
 * @author baranowb
 */
public class SingleConnectorService implements JNPServerNamingConnectorService<SingleConnectorLegacyService> {

    private final InjectedValue<SocketBinding> binding = new InjectedValue<SocketBinding>();
    private final InjectedValue<SocketBinding> rmiBinding = new InjectedValue<SocketBinding>();
    private final InjectedValue<JNPServer> jnpServer = new InjectedValue<JNPServer>();
    private SingleConnectorLegacyService serverConnector;

    public SingleConnectorService() {
    }

    public InjectedValue<JNPServer> getJNPServer() {
        return jnpServer;
    }

    @Override
    public InjectedValue<SocketBinding> getBinding() {
        return binding;
    }

    @Override
    public InjectedValue<SocketBinding> getRmiBinding() {
        return rmiBinding;
    }

    @Override
    public SingleConnectorLegacyService getValue() throws IllegalStateException, IllegalArgumentException {
        return this.serverConnector;
    }

    @Override
    public void start(StartContext startContext) throws StartException {
        try {
            final SocketBinding socketBinding = this.getBinding().getValue();
            SocketBinding rmiSocketBinding = socketBinding;
            if (this.getRmiBinding().getOptionalValue() != null) {
                rmiSocketBinding = this.getRmiBinding().getOptionalValue();
            }
            JNPLogger.ROOT_LOGGER.startSingletonConnectorService(socketBinding.getName(), socketBinding.getAddress(), socketBinding.getAbsolutePort(), 
                    rmiSocketBinding.getName(), rmiSocketBinding.getAddress(), rmiSocketBinding.getAbsolutePort());
            this.serverConnector = new SingleConnectorLegacyService(jnpServer.getValue(), socketBinding.getAddress().getHostName(), socketBinding.getAbsolutePort(),
                    rmiSocketBinding.getAddress().getHostName(), rmiSocketBinding.getAbsolutePort());
            this.serverConnector.start();
        } catch (Exception e) {
            throw JNPMessages.MESSAGES.failedToStartSingletonConnectorService(e);
        }
    }

    @Override
    public void stop(StopContext stopContext) {
        JNPLogger.ROOT_LOGGER.stopSingletonConnectorService();
        try {
            this.serverConnector.stop();
        } catch (Exception ex) {
            JNPLogger.ROOT_LOGGER.couldNotStopSingletonConnectorService(ex);
        }
        this.serverConnector = null;
    }
}
