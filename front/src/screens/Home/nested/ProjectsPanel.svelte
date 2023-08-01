<script lang="ts">
  import { onMount, getContext, createEventDispatcher } from 'svelte';
  import type ProjectService from '@services/ProjectService';
  import type Project from '@types/Project';
  import type { Colors } from '@types/Theme';
  import type Theme from '@types/Theme';
  import ProjectCard from '@components/ProjectCard.svelte';
  import Icon from '@components/Icon.svelte';
  import ProjectCardSkeleton from '@components/ProjectCardSkeleton.svelte';

  const projectService: ProjectService = getContext('ProjectService');
  const theme: Theme = getContext('Theme');
  const colors: Colors = theme.colors;
  const dispatch = createEventDispatcher();
  let projects: Project[] = [];
  export let isOpen: boolean = true;
  export let disableClose: boolean = false;
  $: isLoading = projects.length === 0;

  onMount(loadProjects);

  async function loadProjects() {
    const portfolioIterator = projectService.findAll();

    let hasNext: boolean = true;
    do {
      let { done, value } = await portfolioIterator.next();
      hasNext = !done;
      if (value) {
        projects = [...projects, value];
      }
    } while (hasNext);
  }
</script>

<aside
  class="
    fixed 
    inset-0 
    lg:left-[70%] 
    bg-primary 
    z-10 
    overflow-y-auto 
    translate-x-full 
    ease-in-out 
    duration-300
  "
  style={isOpen ? 'transform: translateX(0);' : ''}
>
  {#if !disableClose}
    <nav class="p-2 pb-0 flex flex-row-reverse">
      <button on:click={() => dispatch('close')}>
        <Icon name="close" size={50} color={colors['on-primary']} />
      </button>
    </nav>
  {/if}
  <div class="p-2 divide-y-8 divide-primary">
    {#if isLoading}
      <ProjectCardSkeleton isOpen />
      <ProjectCardSkeleton />
      <ProjectCardSkeleton />
      <ProjectCardSkeleton />
      <ProjectCardSkeleton />
      <ProjectCardSkeleton />
    {:else}
      {#each projects as project, i}
        <ProjectCard {project} isOpen={i === 0} />
      {/each}
    {/if}
  </div>
</aside>
