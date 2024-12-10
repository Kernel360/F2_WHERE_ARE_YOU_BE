package org.badminton.infrastructure.statistics;

import java.util.concurrent.TimeUnit;

import org.badminton.domain.domain.statistics.DistributedLockProcessor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class DistributeLockProcessorExecutorImpl implements DistributedLockProcessor {

	private final RedissonClient redissonClient;

	@Override
	public void execute(String lockName, long waitMilliSecond, long releaseMilliSecond, Runnable runnable) {
		RLock lock = redissonClient.getLock("lock");
		try {
			boolean isLocked = lock.tryLock(waitMilliSecond, releaseMilliSecond, TimeUnit.MILLISECONDS);
			if (!isLocked) {
				throw new IllegalArgumentException("[" + lockName + "] lock 획득 실패");
			}
			runnable.run();
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
			if (lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}
	}
}
