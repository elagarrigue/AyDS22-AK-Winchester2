package ayds.winchester.songinfo.otherinfo.model.repository

import ayds.winchester.songinfo.moredetails.model.entities.ArtistDescription
import ayds.winchester.songinfo.moredetails.model.entities.EmptyDescription
import ayds.winchester.songinfo.moredetails.model.repository.DescriptionRepositoryImpl
import ayds.winchester.songinfo.moredetails.model.repository.external.ExternalRepository
import ayds.winchester.songinfo.moredetails.model.repository.local.LocalRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Test
import java.lang.Exception

class DescriptionRepositoryTest {
    private val localRepository: LocalRepository = mockk(relaxUnitFun = true)
    private val externalRepository: ExternalRepository = mockk(relaxUnitFun = true)

    private val descriptionRepository: DescriptionRepositoryImpl by lazy {
        DescriptionRepositoryImpl(localRepository, externalRepository)
    }

    @Test
    fun `given existing description should return description and mark it as local`() {
        val description = ArtistDescription("id", "description")
        every { localRepository.getArtistDescription("artist") } returns description

        val result = descriptionRepository.getDescription("artist")

        Assert.assertEquals(description, result)
        Assert.assertTrue(description.isLocallyStored)
    }

    @Test
    fun `given non existing description should get the description and store it`() {
        val description = ArtistDescription("id", "description")
        every { localRepository.getArtistDescription("artist") } returns null
        every { externalRepository.getArtistDescription("artist") } returns description

        val result = descriptionRepository.getDescription("artist")

        Assert.assertEquals(description, result)
        Assert.assertFalse(description.isLocallyStored)
        verify { localRepository.saveDescriptionInDataBase(description) }
    }

    @Test
    fun `given non existing description should return empty description`() {
        every { localRepository.getArtistDescription("artist") } returns null
        every { externalRepository.getArtistDescription("artist") } throws mockk<Exception>()

        val result = descriptionRepository.getDescription("artist")

        Assert.assertEquals(EmptyDescription, result)
    }

    @Test
    fun `given service exception should return empty description`() {
        every { localRepository.getArtistDescription("artist") } returns null
        every { externalRepository.getArtistDescription("artist") } throws mockk<Exception>()

        val result = descriptionRepository.getDescription("artist")

        Assert.assertEquals(EmptyDescription, result)
    }
}