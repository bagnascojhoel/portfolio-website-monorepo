<script lang="ts">
    import ContactsSection from './nested/ContactsSection.svelte';
    import HighlightedFooter from './nested/HighlightedFooter.svelte';
    import IntroductionSection from './nested/IntroductionSection.svelte';
    import ProjectsPanel from './nested/ProjectsPanel.svelte';
    import ShowProjectsButton from './nested/ShowProjectsButton.svelte';
    import type Theme from '@domain/Theme';
    import { getContext } from 'svelte';

    const theme: Theme = getContext('Theme');

    let innerWidth = window.innerWidth;
    $: isMobileScreen = innerWidth < theme.getScreenValue('lg');

    let showProjectsPanel = false;
    function handleShowProjectsPanelSwitch() {
        showProjectsPanel = !showProjectsPanel;
    }
</script>

<svelte:window bind:innerWidth />

<div class="min-h-screen bg-color-background bg-geometric-pattern">
    <main
        class="
      flex
      flex-col
      items-center
      lg:items-start
      container
      m-auto
      min-h-full
      pt-16
      md:pt-32
      lg:pt-16
      2xl:pt-42
      px-6
      relative"
    >
        <IntroductionSection />
        {#if isMobileScreen}
            <ShowProjectsButton
                on:showProjects={handleShowProjectsPanelSwitch}
            />
        {/if}
        <ContactsSection />
        <ProjectsPanel
            isOpen={showProjectsPanel || !isMobileScreen}
            disableClose={!isMobileScreen}
            on:close={handleShowProjectsPanelSwitch}
        />
    </main>

    <HighlightedFooter />
</div>
