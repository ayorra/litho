/*
 * Copyright 2014-present Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.facebook.litho;

import com.facebook.litho.annotations.Event;

/**
 * An {@link Event} that is triggered when a component is render complete.
 *
 * @param hasMounted is true if the component has mounted (i.e. on the screen), false otherwise.
 * @param timestampNanos is the timestamp when the component is rendered complete, the unit is
 *     nanosecond.
 */
@Event
public class RenderCompleteEvent {
  public boolean hasMounted;
  public long timestampNanos;
}
