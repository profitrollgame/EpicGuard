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

import com.google.common.collect.EvictingQueue;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.user.ConnectingUser;
import me.xneox.epicguard.core.util.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Queue;

/**
 * This check caches nicknames of the recently connected users,
 * and uses {@link FuzzySearch} to check similarity between them.
 */
public final class NameSimilarityCheck extends AbstractCheck {
  private final Queue<String> nameHistory = EvictingQueue.create(this.epicGuard.config().nameSimilarityCheck().historySize());

  public NameSimilarityCheck(EpicGuard epicGuard) {
    super(epicGuard, epicGuard.messages().disconnect().nameSimilarity(), epicGuard.config().nameSimilarityCheck().priority());
  }

  @Override
  public boolean isDetected(@NotNull ConnectingUser user) {
    return this.evaluate(this.epicGuard.config().nameSimilarityCheck().checkMode(), () -> {
      synchronized (this.nameHistory) {
        String sanitized = StringUtils.sanizitzeString(user.nickname());
        for (String nick : this.nameHistory) {
          if (StringUtils.stringSimilarityInPercent(nick, sanitized) >=
                  this.epicGuard.config().nameSimilarityCheck().maxSimilarityPercent()) {
            return true;
          }
        }

        // They passed the check, add them to the history.
        this.nameHistory.add(sanitized);
        return false;
      }
    });
  }
}
