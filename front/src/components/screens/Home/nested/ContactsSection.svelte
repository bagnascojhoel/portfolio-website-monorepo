<script lang="ts">
  import { getContext } from 'svelte';
  import Icon from '@components/Icon.svelte';
  import {
    PERSONAL_LINKEDIN_URL,
    PERSONAL_GITHUB_URL,
  } from '@assets/text/contact-info';
  import type Theme from '@model/Theme';

  const { colors }: Theme = getContext('Theme');

  const GITHUB_LABEL = 'github';
  const LINKEDIN_LABEL = 'linkedin';
</script>

<section class="mt-24 lg:mt-0">
  <ul class="mt-7 flex flex-row justify-center gap-4">
    <li>
      <a href={PERSONAL_LINKEDIN_URL} target="_blank" rel="noopener noreferrer">
        <Icon
          name="linkedin"
          color={colors['primary-variant']}
          label={LINKEDIN_LABEL}
        />
      </a>
    </li>
    <li>
      <a href={PERSONAL_GITHUB_URL} target="_blank" rel="noopener noreferrer">
        <Icon
          name="github"
          color={colors['primary-variant']}
          label={GITHUB_LABEL}
        />
      </a>
    </li>
  </ul>
</section>
