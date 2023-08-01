<script lang="ts">
  import { createEventDispatcher } from 'svelte';

  const dispatch = createEventDispatcher();

  function handleClick() {
    dispatch('showProjects');
  }
</script>

<button
  class="w-[85%] font-mono text-base text-primary-variant border-primary-variant border-2 p-2"
  on:click={handleClick}
>
  my projects
</button>
