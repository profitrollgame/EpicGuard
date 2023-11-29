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

package me.xneox.epicguard.core.storage;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * AddressMeta holds information about an IP address.
 * All known AddressMeta's are stored in the database and cached in the {@link StorageManager}
 */
public final class AddressMeta {
  private final List<String> nicknames;
  private boolean blacklisted;
  private boolean whitelisted;
  private boolean needsSave;

  public AddressMeta(boolean blacklisted, boolean whitelisted, @NotNull List<String> nicknames, boolean needsSave) {
    this.blacklisted = blacklisted;
    this.whitelisted = whitelisted;
    this.nicknames = nicknames;
    this.needsSave = needsSave;
  }

  public boolean blacklisted() {
    return this.blacklisted;
  }

  public void blacklisted(boolean blacklisted) {
    this.blacklisted = blacklisted;
    this.needsSave = true;
  }

  public boolean whitelisted() {
    return this.whitelisted;
  }

  public void whitelisted(boolean whitelisted) {
    this.whitelisted = whitelisted;
    this.needsSave = true;
  }

  @NotNull
  public List<String> nicknames() {
    return Collections.unmodifiableList(this.nicknames);
  }

  public void addNickname(@NotNull String nickname) {
    this.nicknames.add(nickname);
    this.needsSave = true;
  }

  public boolean needsSave() {
    return this.needsSave;
  }

  public void needsSave(boolean needsSave) {
    this.needsSave = needsSave;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof final AddressMeta that)) {
      return false;
    }
    return this.blacklisted == that.blacklisted
            && this.whitelisted == that.whitelisted
            && Objects.equals(this.nicknames, that.nicknames);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nicknames, blacklisted, whitelisted);
  }
}
