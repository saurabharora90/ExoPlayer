/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.exoplayer.chunk;

import com.google.android.exoplayer.Format;
import com.google.android.exoplayer.drm.DrmInitData;
import com.google.android.exoplayer.extractor.RollingSampleBuffer;
import com.google.android.exoplayer.upstream.DataSource;
import com.google.android.exoplayer.upstream.DataSpec;

/**
 * A base implementation of {@link MediaChunk}, for chunks that contain a single track.
 * <p>
 * Loaded samples are output to a {@link RollingSampleBuffer}.
 */
public abstract class BaseMediaChunk extends MediaChunk {

  private RollingSampleBuffer trackOutput;
  private int firstSampleIndex;

  /**
   * @param dataSource A {@link DataSource} for loading the data.
   * @param dataSpec Defines the data to be loaded.
   * @param trigger The reason for this chunk being selected.
   * @param format The format of the stream to which this chunk belongs.
   * @param startTimeUs The start time of the media contained by the chunk, in microseconds.
   * @param endTimeUs The end time of the media contained by the chunk, in microseconds.
   * @param chunkIndex The index of the chunk.
   */
  public BaseMediaChunk(DataSource dataSource, DataSpec dataSpec, int trigger, Format format,
      long startTimeUs, long endTimeUs, int chunkIndex) {
    super(dataSource, dataSpec, trigger, format, startTimeUs, endTimeUs, chunkIndex);
  }

  /**
   * Initializes the chunk for loading, setting the {@link RollingSampleBuffer} that will receive
   * samples as they are loaded.
   *
   * @param trackOutput The output that will receive the loaded samples.
   */
  public void init(RollingSampleBuffer trackOutput) {
    this.trackOutput = trackOutput;
    this.firstSampleIndex = trackOutput.getWriteIndex();
  }

  /**
   * Returns the index of the first sample in the output that was passed to
   * {@link #init(RollingSampleBuffer)} that will originate from this chunk.
   */
  public final int getFirstSampleIndex() {
    return firstSampleIndex;
  }

  /**
   * Gets the {@link DrmInitData} corresponding to the chunk.
   *
   * @return The {@link DrmInitData} corresponding to this chunk.
   */
  public abstract DrmInitData getDrmInitData();

  /**
   * Returns the track output most recently passed to {@link #init(RollingSampleBuffer)}.
   */
  protected final RollingSampleBuffer getTrackOutput() {
    return trackOutput;
  }

}
