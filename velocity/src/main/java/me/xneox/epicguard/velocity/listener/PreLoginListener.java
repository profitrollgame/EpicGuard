/*
 * EpicGuard is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EpicGuard is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package me.xneox.epicguard.velocity.listener;

import com.google.inject.Inject;
import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.event.EventTask;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.connection.PreLoginEvent;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.handler.PreLoginHandler;
import me.xneox.epicguard.velocity.EpicGuardVelocity;

public final class PreLoginListener extends PreLoginHandler implements Listener<PreLoginEvent> {
    @Inject
    private EventManager eventManager;
    @Inject
    private EpicGuardVelocity plugin;

    @Inject
    public PreLoginListener(final EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Override
    public void register() {
        this.eventManager.register(plugin, PreLoginEvent.class, PostOrder.FIRST, this);
    }

    @Override
    public EventTask executeAsync(PreLoginEvent event) {
        return EventTask.withContinuation((continuation) -> {
            final String address = event.getConnection().getRemoteAddress().getAddress().getHostAddress();
            final String nickname = event.getUsername();
            this.onPreLogin(address, nickname)
                    .ifPresent(result -> event.setResult(PreLoginEvent.PreLoginComponentResult.denied(result)));
            continuation.resume();
        });
    }
}
