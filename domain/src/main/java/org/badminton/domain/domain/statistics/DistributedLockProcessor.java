package org.badminton.domain.domain.statistics;

public interface DistributedLockProcessor {

	void execute(String lockName, long waitMilliSecond, long releaseMilliSecond, Runnable runnable);
}
