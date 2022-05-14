package ayds.winchester.otherInfo.model.repository


import ayds.winchester.songinfo.moredetails.model.entities.ArtistDescription
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
/*
    @Test
    fun `given existing song by different term should get the song and update it`() {
        val song = SpotifySong("id", "name", "artist", "album", "date", "url", "image",
            DatePrecision.YEAR,false)
        every { spotifyLocalStorage.getSongByTerm("term") } returns null
        every { spotifyTrackService.getSong("term") } returns song
        every { spotifyLocalStorage.getSongById("id") } returns song

        val result = songRepository.getSongByTerm("term")

        Assert.assertEquals(song, result)
        Assert.assertFalse(song.isLocallyStored)
        verify { spotifyLocalStorage.updateSongTerm("term", "id") }
    }

    @Test
    fun `given non existing song by term should return empty song`() {
        every { spotifyLocalStorage.getSongByTerm("term") } returns null
        every { spotifyTrackService.getSong("term") } returns null

        val result = songRepository.getSongByTerm("term")

        Assert.assertEquals(EmptySong, result)
    }

    @Test
    fun `given service exception should return empty song`() {
        every { spotifyLocalStorage.getSongByTerm("term") } returns null
        every { spotifyTrackService.getSong("term") } throws mockk<Exception>()

        val result = songRepository.getSongByTerm("term")

        Assert.assertEquals(EmptySong, result)
    }*/

}