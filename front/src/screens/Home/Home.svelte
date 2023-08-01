<script lang="ts">
  import type Theme from '@types/Theme';
  import { getContext } from 'svelte';
  import ContactsSection from './nested/ContactsSection.svelte';
  import HighlightedFooter from './nested/HighlightedFooter.svelte';
  import IntroductionSection from './nested/IntroductionSection.svelte';
  import ProjectsPanel from './nested/ProjectsPanel.svelte';
  import ShowProjectsButton from './nested/ShowProjectsButton.svelte';

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
      pt-32
      md:pt-48 
      lg:pt-60 
      2xl:pt-72 
      px-6 
      relative"
  >
    <IntroductionSection />
    {#if isMobileScreen}
      <ShowProjectsButton on:showProjects={handleShowProjectsPanelSwitch} />
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
