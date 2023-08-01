<script lang="ts">
  import { getContext } from 'svelte';
  import Icon from '@components/Icon.svelte';
  import type Theme from '@types/Theme';
  import type Project from '@types/Project';

  export let project: Project;
  export let isOpen: boolean = true;

  const { colors }: Theme = getContext('Theme');
  const OUT_LINK_LABEL: string = 'website';
  const GITHUB_LABEL: string = 'github';

  function triggerOpenSwitch() {
    isOpen = !isOpen;
  }
</script>

<div
  on:click={triggerOpenSwitch}
  on:keypress={triggerOpenSwitch}
  class="p-4 bg-background cursor-pointer duration-100 card"
>
  <h3 class="text-xl text-primary font-mono">
    {project.title}
  </h3>
  <div
    class="ease-in-out duration-300 card-content"
    class:card-content-open={isOpen}
  >
    <ul class="mt-6 flex flex-row flex-wrap">
      {#each project.tags as tag}
        <li
          class="mb-2 mr-2 px-2 rounded-full bg-primary-variant lowercase font-mono text-sm text-on-primary font-bold"
        >
          {tag}
        </li>
      {/each}
    </ul>

    <p class="mt-3 text-base text-primary-variant font-sans ">
      {project.description}
    </p>

    <div class="mt-4 flex flex-row gap-6">
      <a
        on:click|stopPropagation={() => {}}
        href={project.githubUrl}
        target="_blank"
        rel="noreferrer"
      >
        <Icon
          name="github"
          size={36}
          color={colors['primary-variant']}
          label={GITHUB_LABEL}
        />
      </a>
      {#if project.websiteUrl}
        <a
          on:click|stopPropagation={() => {}}
          href={project.websiteUrl}
          target="_blank"
          rel="noreferrer"
        >
          <Icon
            name="out-link"
            size={36}
            color={colors['primary-variant']}
            label={OUT_LINK_LABEL}
          />
        </a>
      {/if}
    </div>
  </div>
</div>

<style>
  .card-content {
    max-height: 0;
    overflow: hidden;
  }
  .card-content-open {
    max-height: 500px;
  }

  .card:hover:has(.card-content:not(.card-content-open)) {
    margin-left: 16px;
  }
</style>
