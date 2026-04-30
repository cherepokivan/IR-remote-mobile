package com.irremote.app.domain.usecase

import com.irremote.app.domain.model.IRCommand
import com.irremote.app.domain.repository.IRCommandRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCommandsByDeviceIdUseCase @Inject constructor(
    private val irCommandRepository: IRCommandRepository
) {
    operator fun invoke(deviceId: Long): Flow<List<IRCommand>> {
        return irCommandRepository.getCommandsByDeviceId(deviceId)
    }
}

class GetCommandByIdUseCase @Inject constructor(
    private val irCommandRepository: IRCommandRepository
) {
    suspend operator fun invoke(commandId: Long): IRCommand? {
        return irCommandRepository.getCommandById(commandId)
    }
}

class GetCommandByNameUseCase @Inject constructor(
    private val irCommandRepository: IRCommandRepository
) {
    suspend operator fun invoke(deviceId: Long, commandName: String): IRCommand? {
        return irCommandRepository.getCommandByName(deviceId, commandName)
    }
}

class AddCommandUseCase @Inject constructor(
    private val irCommandRepository: IRCommandRepository
) {
    suspend operator fun invoke(command: IRCommand): Long {
        return irCommandRepository.insertCommand(command)
    }
}

class AddCommandsUseCase @Inject constructor(
    private val irCommandRepository: IRCommandRepository
) {
    suspend operator fun invoke(commands: List<IRCommand>) {
        irCommandRepository.insertCommands(commands)
    }
}

class UpdateCommandUseCase @Inject constructor(
    private val irCommandRepository: IRCommandRepository
) {
    suspend operator fun invoke(command: IRCommand) {
        irCommandRepository.updateCommand(command)
    }
}

class DeleteCommandUseCase @Inject constructor(
    private val irCommandRepository: IRCommandRepository
) {
    suspend operator fun invoke(command: IRCommand) {
        irCommandRepository.deleteCommand(command)
    }
}
