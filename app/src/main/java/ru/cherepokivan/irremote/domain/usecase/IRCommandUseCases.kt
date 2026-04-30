package ru.cherepokivan.irremote.domain.usecase

import ru.cherepokivan.irremote.domain.model.IRCommand
import ru.cherepokivan.irremote.domain.repository.IRCommandRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class IRCommandUseCases(
    val getCommandsByDeviceId: GetCommandsByDeviceIdUseCase,
    val addCommand: AddCommandUseCase,
    val updateCommand: UpdateCommandUseCase,
    val deleteCommand: DeleteCommandUseCase,
    val getCommandById: GetCommandByIdUseCase,
    val deleteCommandsByDeviceId: DeleteCommandsByDeviceIdUseCase,
    val searchCommands: SearchCommandsUseCase
)

class GetCommandsByDeviceIdUseCase @Inject constructor(
    private val repository: IRCommandRepository
) {
    operator fun invoke(deviceId: Long): Flow<List<IRCommand>> {
        return repository.getCommandsByDeviceId(deviceId)
    }
}

class AddCommandUseCase @Inject constructor(
    private val repository: IRCommandRepository
) {
    suspend operator fun invoke(command: IRCommand): Long {
        return repository.addCommand(command)
    }
}

class UpdateCommandUseCase @Inject constructor(
    private val repository: IRCommandRepository
) {
    suspend operator fun invoke(command: IRCommand) {
        repository.updateCommand(command)
    }
}

class DeleteCommandUseCase @Inject constructor(
    private val repository: IRCommandRepository
) {
    suspend operator fun invoke(command: IRCommand) {
        repository.deleteCommand(command)
    }
}

class GetCommandByIdUseCase @Inject constructor(
    private val repository: IRCommandRepository
) {
    suspend operator fun invoke(id: Long): IRCommand? {
        return repository.getCommandById(id)
    }
}

class DeleteCommandsByDeviceIdUseCase @Inject constructor(
    private val repository: IRCommandRepository
) {
    suspend operator fun invoke(deviceId: Long) {
        repository.deleteCommandsByDeviceId(deviceId)
    }
}

class SearchCommandsUseCase @Inject constructor(
    private val repository: IRCommandRepository
) {
    operator fun invoke(deviceId: Long, query: String): Flow<List<IRCommand>> {
        return repository.searchCommands(deviceId, query)
    }
}
