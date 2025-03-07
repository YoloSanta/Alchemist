package ltd.matrixstudios.alchemist.service.expirable

import io.github.nosequel.data.DataStoreType
import ltd.matrixstudios.alchemist.Alchemist
import ltd.matrixstudios.alchemist.models.grant.types.TagGrant
import java.util.*
import java.util.concurrent.CompletableFuture

object TagGrantService : ExpiringService<TagGrant>() {

    var handler = Alchemist.dataHandler.createStoreType<UUID, TagGrant>(DataStoreType.MONGO)


    fun getValues(): CompletableFuture<Collection<TagGrant>> {
        return handler.retrieveAllAsync()
    }

    fun save(tagGrant: TagGrant) {
        handler.storeAsync(tagGrant.uuid, tagGrant)
    }

    override fun clearOutModels() {
        getValues().get().forEach { it.expirable.isActive() }
    }

}