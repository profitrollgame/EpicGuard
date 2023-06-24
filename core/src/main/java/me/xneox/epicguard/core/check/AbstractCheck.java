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

package me.xneox.epicguard.core.check;

import java.util.List;
import java.util.function.BooleanSupplier;

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.user.ConnectingUser;
import me.xneox.epicguard.core.util.TextUtils;
import me.xneox.epicguard.core.util.ToggleState;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public abstract sealed class AbstractCheck implements Comparable<AbstractCheck>
    permits
        AccountLimitCheck,
        BlacklistCheck,
        GeographicalCheck,
        LockdownCheck,
        NameSimilarityCheck,
        NicknameCheck,
        ProxyCheck,
        ReconnectCheck,
        ServerListCheck
{

    protected final EpicGuard epicGuard;

    private final int priority;
    private final Component detectionMessage;

    protected AbstractCheck(@NotNull EpicGuard epicGuard, @NotNull List<String> detectionMessage, int priority) {
        this.epicGuard = epicGuard;
        this.detectionMessage = TextUtils.multilineComponent(detectionMessage);
        this.priority = priority;
    }

    /**
     * Method containing the check's logic, it's return value determines if
     * the user will be disconnected or if the pipeline will continue checking the user.
     *
     * @param user the connecting user.
     * @return true if detected, false if not
     */
    public abstract boolean isDetected(@NotNull ConnectingUser user);

    /**
     * This method asserts the following behaviour based on the provided {@link ToggleState}:
     * - If the state is ALWAYS, it will evaluate and return the value of the specified expression.
     * - If the state is ATTACK, it will evaluate and return the value of the expression ONLY if there's an attack.
     * - If the state is NEVER, it will return false.
     * <br/>
     * This method invokes the expression sync, so it is advised
     * to put all expensive operations inside the expression.
     *
     * @param state      the configured {@link ToggleState} for this check
     * @param expression the base check runnable if the state is met
     * @return The return value is based on the check's behaviour. True means positive detection,
     * false means negative.
     */
    public boolean evaluate(ToggleState state, BooleanSupplier expression) {
        if (state == ToggleState.ALWAYS || state == ToggleState.ATTACK && this.epicGuard.attackManager().isUnderAttack()) {
            return expression.getAsBoolean();
        }

        return false;
    }

    /**
     * A formatted {@link Component} which is a disconnect message for this check.
     *
     * @return disconnect message of this check
     */
    @NotNull
    public Component detectionMessage() {
        return this.detectionMessage;
    }

    /**
     * Compares the priority of this check to another check.
     * Used to automatically sort the checks in the pipeline.
     *
     * @param other the other check
     * @return result of the method {@link Integer#compare(int, int)}
     */
    @Override
    public int compareTo(@NotNull AbstractCheck other) {
        return Integer.compare(other.priority, this.priority);
    }
}
