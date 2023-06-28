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

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.user.ConnectingUser;
import org.jetbrains.annotations.NotNull;

/**
 * This check limits how many accounts can be created on one address.
 */
public final class AccountLimitCheck extends AbstractCheck {
  public AccountLimitCheck(EpicGuard epicGuard) {
    super(epicGuard, epicGuard.messages().disconnect().accountLimit(), epicGuard.config().accountLimitCheck().priority());
  }

  @Override
  public boolean isDetected(@NotNull ConnectingUser user) {
    if (!this.epicGuard.config().accountLimitCheck().enabled()) {
      return false;
    }

    var accounts = this.epicGuard.storageManager().addressMeta(user.address()).nicknames();

    // Account is allowed on this address because they already passed this check before
    if (accounts.contains(user.nickname())) {
      return false;
    }

    int accountLimit = this.epicGuard.attackManager().isUnderAttack() ?
            this.epicGuard.config().accountLimitCheck().attackAccountLimit()
            : this.epicGuard.config().accountLimitCheck().accountLimit();

    return accounts.size() >= accountLimit;
  }
}
